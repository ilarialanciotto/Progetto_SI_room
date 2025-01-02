package com.example.progetto_si.Cliente.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.R
import com.example.progetto_si.Pacchetto.PacchettoAdapter
import com.example.progetto_si.Pacchetto.Pacchetto
import com.example.progetto_si.Pacchetto.PacchettoViewModel

class AcquistaPacchettoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val pacchettoViewModel: PacchettoViewModel by viewModels()
    private lateinit var pacchettoAdapter: PacchettoAdapter
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacchetti)

        recyclerView = findViewById(R.id.recyclerViewPacchetti)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Recupera lo username passato come extra
        username = intent.getStringExtra("EXTRA_USERNAME") ?: "N/D"

        // Inizializza l'adapter
        pacchettoAdapter = PacchettoAdapter { pacchetto ->
            acquistaPacchetto(pacchetto)
        }
        recyclerView.adapter = pacchettoAdapter

        // Osserva i pacchetti dal ViewModel
        pacchettoViewModel.pacchetti.observe(this) { pacchetti ->
            pacchettoAdapter.submitList(pacchetti)
        }

        // Carica i pacchetti
        pacchettoViewModel.caricaPacchetti()
    }

    // Logica per acquistare un pacchetto
    private fun acquistaPacchetto(pacchetto: Pacchetto) {
        pacchettoViewModel.acquistaPacchetto(pacchetto, username) { successo ->
            if (successo) {
                Toast.makeText(this, "Acquisto completato: ${pacchetto.nome}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Errore durante l'acquisto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
