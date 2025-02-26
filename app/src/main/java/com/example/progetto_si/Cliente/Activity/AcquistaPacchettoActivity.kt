package com.example.progetto_si.Cliente.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.Acquisti.Acquisti
import com.example.progetto_si.Acquisti.AcquistiViewModel
import com.example.progetto_si.ClassiUtili.PacchettoAdapter
import com.example.progetto_si.Cliente.Room.ClienteViewModel
import com.example.progetto_si.R
import com.example.progetto_si.Pacchetto.Pacchetto
import com.example.progetto_si.Pacchetto.PacchettoViewModel
import kotlinx.coroutines.launch

class AcquistaPacchettoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val pacchettoViewModel: PacchettoViewModel by viewModels()
    private lateinit var pacchettoAdapter: PacchettoAdapter
    private lateinit var username: String
    private lateinit var password : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacchetti)

        recyclerView = findViewById(R.id.recyclerViewPacchetti)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Recupera lo username passato come extra
        username = intent.getStringExtra("EXTRA_USERNAME") ?: "N/D"
        password = intent.getStringExtra("EXTRA_PASSWORD") ?: "N/D"

        // Inizializza l'adapter
        pacchettoAdapter = PacchettoAdapter(true) { pacchetto ->
            acquistaPacchetto(pacchetto)
        }
        recyclerView.adapter = pacchettoAdapter

        // Osserva i pacchetti dal ViewModel
        pacchettoViewModel.pacchetti.observe(this) { pacchetti ->
            pacchettoAdapter.submitList(pacchetti)
        }

    }

    // Logica per acquistare un pacchetto
    private fun acquistaPacchetto(pacchetto: Pacchetto) {
        var idP = pacchetto.id.toInt()
        var clienteWM = ClienteViewModel(application)
        var acqWM = AcquistiViewModel(application)
        lifecycleScope.launch {
            clienteWM.getIdCliente(username,password) { idC->
                var acqOk = Acquisti(cliente = idC,
                    pacchetto = idP)
                acqWM.insert(acqOk)
            }
        }
        Toast.makeText(this, getString(R.string.acquisto_andato_a_buon_fine), Toast.LENGTH_SHORT).show()
    }
}