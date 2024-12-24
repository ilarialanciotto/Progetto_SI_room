package com.example.progetto_si

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var DL : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        DL= findViewById(R.id.drawer_layout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_icon)

        val Web : WebView = findViewById(R.id.webVW)
        Web.webViewClient = WebViewClient()
        Web.loadUrl("https://www.nexsys.it/sicurezza-informatica-e-le-policy-piu-importanti/")

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_map -> {
                    val intent = Intent(this, mappaView::class.java)
                    startActivity(intent)
                }
            }
            DL.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.main_menu),menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            DL.openDrawer(GravityCompat.START)
            return true
        }

        when (item.itemId) {

            R.id.menu_login -> {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_register -> {
                val intent = Intent(this, Register::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}