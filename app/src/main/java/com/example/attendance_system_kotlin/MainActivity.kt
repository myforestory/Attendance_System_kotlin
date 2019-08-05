package com.example.attendance_system_kotlin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.SpannableString
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.beust.klaxon.Klaxon
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var bundle: Bundle = Bundle()
    private var mainInfoList = ArrayList<Maininfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundle.putInt("ggggg", 123)
        title = ""
        setContentView(R.layout.activity_main)
        drawerAction()
        setInitialMainInfo()
        updateTitleTime()


    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.back_home) {
            val btnUpdate: Button = findViewById(R.id.back_home)
            var status: SharedPreferences = getSharedPreferences("status", Context.MODE_PRIVATE)
            status.edit().putInt("statusCode", 1)
            setBtUpdateStyle()
        } else if (id == R.id.log_out) {
            logout()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun drawerAction() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        var mTitle: CharSequence? = title
        var mDrawerTitle: CharSequence? = title

        val mDrawerToggle: ActionBarDrawerToggle = object: ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ){
            override fun onDrawerClosed(view: View) {
                title = mTitle
                invalidateOptionsMenu()
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                var tvUserName: TextView = findViewById(R.id.tvUserName)
                var tvUserEmail: TextView = findViewById(R.id.tvUserEmail)
                var nameString = SpannableString(bundle.getString("name"))
                var emailString = SpannableString(bundle.getString("email"))
                tvUserName.text = nameString
                tvUserEmail.text = emailString
                title = mDrawerTitle
                invalidateOptionsMenu()
            }
        }
        drawerLayout.addDrawerListener(mDrawerToggle)
    }

    private fun setInitialMainInfo() {
        var c: Calendar = Calendar.getInstance()
//        var nowYear: Int = c.get(Calendar.YEAR)
//        var nowMonth: Int = c.get(Calendar.MONTH)+1
//        var nowDate: Int = c.get(Calendar.DATE)
        var nowYearString: String = c.get(Calendar.YEAR).toString()
        var nowMonthString: String = (c.get(Calendar.MONTH)+1).toString()
        var nowDateString: String = c.get(Calendar.DATE).toString()
        getMainInfoData(nowYearString, nowMonthString)
    }

    private fun getMainInfoData(year: String, month:String) {
        var dataTime: String = "$year-$month"
        var loginToken: String = bundle.getString("loginToken")
        var userId: String = bundle.getString("userId")
        var getDataUrl: String  = "http://kintai-api.ios.tokyo/user/$userId/attendance/$dataTime";
        var mainInfoList2 = ArrayList<Maininfo>();

        Log.d("looginToken", loginToken)

        if(!loginToken.isEmpty() && !userId.isEmpty()) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .header("Authorization", loginToken)
                .url(getDataUrl)
                .build()
            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, e.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("onFailure", e.toString())
                    }
                }

                override fun onResponse(call: Call?, response: Response?) {
                    var reponsebody = response!!.body().string()
                    mainInfoList2.clear()
                    var result = Klaxon().parse<Maininfo>(reponsebody)
                    Log.d("123", result.toString());
                }
            })
        }
    }

    private fun updateTitleTime () {

    }

    private fun setBtUpdateStyle() {

    }

    private fun logout() {

    }

    private var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if(msg?.what == 0) {

            }
        }
    }
}
