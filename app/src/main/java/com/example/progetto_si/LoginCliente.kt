package com.example.progetto_si

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.example.progetto_si.Registrazione.RegistrazioniViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

class LoginCliente : AppCompatActivity() {

    lateinit var DL : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_cliente)
        setSupportActionBar(findViewById(R.id.toolbar_C))

        val search : SearchView = findViewById(R.id.CercaVW)
        var registrazioniViewModel = RegistrazioniViewModel(application)
        val lista : ListView = findViewById(R.id.ListVW)
        var adapter: ArrayAdapter<String>

        lifecycleScope.launch {
            registrazioniViewModel.getAllnames { items->
                adapter = ArrayAdapter(this@LoginCliente, android.R.layout.simple_list_item_1, items)
                lista.adapter = adapter

                search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        val searchText = newText.orEmpty()
                        if (searchText.isNotEmpty()) {
                            registrazioniViewModel.searchNames(searchText){ results->
                                adapter.clear()
                                adapter.addAll(results)
                                lista.visibility = if (results.isEmpty()) View.GONE else View.VISIBLE
                            }
                        } else {
                            lista.visibility = View.GONE
                        }
                        adapter.notifyDataSetChanged()
                        return true
                    }
                })

            }
        }

        DL= findViewById(R.id.drawer_layout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_icon)

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
        menuInflater.inflate((R.menu.toolbar_menu),menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            DL.openDrawer(GravityCompat.START)
            return true
        }

        when (item.itemId) {
            R.id.acquisti -> {
                return true
            }
            R.id.messaggi -> {
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}