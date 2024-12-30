package com.example.progetto_si.Login

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.progetto_si.R

class DashboardAdmin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_admin)

        // Recupera il nome passato tramite Intent
        val nome = intent.getStringExtra("nome") ?: "Amministratore"

        // Imposta il messaggio di benvenuto
        val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
        welcomeTextView.text = "Benvenuto, $nome!"
    }
}
