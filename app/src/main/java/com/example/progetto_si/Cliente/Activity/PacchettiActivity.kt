package com.example.progetto_si.Cliente.Activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.ClassiUtili.PacchettoAdapter
import com.example.progetto_si.R
import com.example.progetto_si.Acquisti.AcquistiViewModel
import com.example.progetto_si.Cliente.Room.ClienteViewModel
import kotlinx.coroutines.launch
class PacchettiActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pacchettoAdapter: PacchettoAdapter
    private lateinit var username: String
    private lateinit var password: String
    private val acquistiViewModel: AcquistiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacchetti)

        recyclerView = findViewById(R.id.recyclerViewPacchetti)
        recyclerView.layoutManager = LinearLayoutManager(this)
        pacchettoAdapter = PacchettoAdapter(false) { pacchetto -> }
        recyclerView.adapter = pacchettoAdapter

        username = intent.getStringExtra("EXTRA_USERNAME") ?: "N/D"
        password = intent.getStringExtra("EXTRA_PASSWORD") ?: "N/D"

        val clienteWM = ClienteViewModel(application)

        // Lancia coroutine legate al ciclo di vita dell'Activity
        lifecycleScope.launch {
            clienteWM.getIdCliente(username, password) { idC ->
                if (isActivityActive()) { // Controlla lo stato dell'Activity
                    acquistiViewModel.getPacchettoById(idC) { pacchetti ->
                        if (isActivityActive()) { // Controlla ancora prima di aggiornare la UI
                            pacchettoAdapter.submitList(pacchetti)
                        }
                    }
                }
            }
        }
    }

    /**
     * Funzione per verificare se l'Activity è attiva.
     */
    private fun isActivityActive(): Boolean {
        return !isFinishing && !isDestroyed
    }
}
