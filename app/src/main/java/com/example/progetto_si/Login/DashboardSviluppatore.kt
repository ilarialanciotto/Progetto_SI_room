package com.example.progetto_si.Login

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.progetto_si.R

class DashboardSviluppatore : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_sviluppatore)

        // Recupera il nome passato tramite Intent
        val nome = intent.getStringExtra("nome") ?: "Sviluppatore"

        // Imposta il messaggio di benvenuto
        val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
        welcomeTextView.text = "Benvenuto, $nome!"
    }
}
