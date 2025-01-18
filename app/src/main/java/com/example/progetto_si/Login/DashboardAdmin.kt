package com.example.progetto_si.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.progetto_si.Admin.GestioneClientiActivity
import com.example.progetto_si.Admin.GestionePacchettiActivity
import com.example.progetto_si.Admin.GestioneSviluppatoriActivity
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
        // Navigazione verso Gestione Clienti
        val btnGestioneClienti = findViewById<Button>(R.id.btn_gestione_clienti)
        btnGestioneClienti.setOnClickListener {
            val intent = Intent(this, GestioneClientiActivity::class.java)
            startActivity(intent)
        }

        // Navigazione verso Gestione Pacchetti
        val btnGestionePacchetti = findViewById<Button>(R.id.btn_gestione_pacchetti)
        btnGestionePacchetti.setOnClickListener {
            val intent = Intent(this, GestionePacchettiActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btn_gestione_sviluppatori).setOnClickListener {
            val intent = Intent(this, GestioneSviluppatoriActivity::class.java)
            startActivity(intent)
        }
    }
}
