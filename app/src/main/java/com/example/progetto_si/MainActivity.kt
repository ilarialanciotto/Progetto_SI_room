package com.example.progetto_si

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.lifecycleScope
import com.example.progetto_si.Admin.Admin
import com.example.progetto_si.Cliente.Room.Cliente
import com.example.progetto_si.Login.Login
import com.example.progetto_si.Sviluppatore.Sviluppatore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val Web : WebView = findViewById(R.id.webVW)
        Web.webViewClient = WebViewClient()
        Web.settings.javaScriptEnabled = true

        //sito creato da noi
        Web.loadUrl("https://cybersicuri.certfin.it/")

        // Popola il database
        lifecycleScope.launch(Dispatchers.IO) {
            val db = MyDatabase.getDatabase(applicationContext)

            // Popolamento della tabella Cliente
            db.ClienteDao().insert(
                Cliente(
                    nome = "Mario",
                    cognome = "Rossi",
                    email = "mario.rossi@example.com",
                    password = "password123",
                    telefono = "1234567890",
                    azienda = "Example Corp",
                    tipo = "cliente"
                )
            )
            Log.d("DatabasePopulation", "Cliente Mario Rossi inserito.")

            db.ClienteDao().insert(
                Cliente(
                    nome = "Luca",
                    cognome = "Bianchi",
                    email = "luca.bianchi@example.com",
                    password = "securePass",
                    telefono = "0987654321",
                    azienda = "Tech Corp",
                    tipo = "cliente"
                )
            )
            Log.d("DatabasePopulation", "Cliente Luca Bianchi inserito.")

            // Popolamento della tabella Admin
            db.AdminDao().insert(
                Admin(
                    nome = "Admin",
                    cognome = "SuperUser",
                    email = "admin@example.com",
                    password = "admin123",
                    ruolo = "admin"
                )
            )
            Log.d("DatabasePopulation", "Admin SuperUser inserito.")

            // Popolamento della tabella Sviluppatore
            db.SviluppatoreDao().insert(
                Sviluppatore(
                    nome = "Marco",
                    cognome = "Verdi",
                    email = "marco.verdi@example.com",
                    password = "developer123",
                    livello = "senior",
                    progetti = "Progetto A, Progetto B",
                    tipo = "sviluppatore"
                )
            )
            Log.d("DatabasePopulation", "Sviluppatore Marco Verdi inserito.")

            Log.d("DatabasePopulation", "Popolamento del database completato.")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.main_menu),menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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
            R.id.mappa -> {
                val intent = Intent(this, mappaView::class.java)
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}