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

class LoginActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var bundle: Bundle = Bundle()
    private var mainInfoList = ArrayList<Maininfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        findViews()

    }

    private fun findViews() {
        var btnLogin: Button = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener(onClickListener)

    }

    var onClickListener = {view: View ->

    }

}
