package com.example.progetto_si.Admin

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.ClassiUtili.ClienteAdapter
import com.example.progetto_si.Cliente.Room.Cliente
import com.example.progetto_si.Cliente.Room.ClienteViewModel
import com.example.progetto_si.R
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.progetto_si.Acquisti.AcquistiViewModel
import kotlinx.coroutines.launch

class GestioneClientiActivity : AppCompatActivity() {

    private lateinit var clienteAdapter: ClienteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestione_clienti)

        var AcqWM = AcquistiViewModel(application)
        // Configura l'adattatore per la RecyclerView
        clienteAdapter = ClienteAdapter(AcqWM)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_clienti)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configura il ViewModel per osservare i dati
        recyclerView.adapter = clienteAdapter

        var clientiWM = ClienteViewModel(application)
        lifecycleScope.launch{
            clientiWM.getAllClients { clienti ->
                clienteAdapter.submitList(clienti) }
        }


        // Configura il bottone per aggiungere un cliente
        val btnAggiungiCliente = findViewById<Button>(R.id.btnAggiungiCliente)
        btnAggiungiCliente.setOnClickListener {
            // Mostra il dialog per aggiungere un cliente
            showAddClienteDialog(clientiWM)
        }
    }

    // Metodo per mostrare il dialog per aggiungere un cliente
    private fun showAddClienteDialog(clienteWM: ClienteViewModel) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_cliente, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Aggiungi Cliente")
            .setView(dialogView)
            .setPositiveButton("Aggiungi") { _, _ ->
                val nome = dialogView.findViewById<EditText>(R.id.etNome)?.text.toString()
                val cognome = dialogView.findViewById<EditText>(R.id.etCognome)?.text.toString()
                val email = dialogView.findViewById<EditText>(R.id.etEmail)?.text.toString()
                val password = dialogView.findViewById<EditText>(R.id.etPassword)?.text.toString()
                val telefono = dialogView.findViewById<EditText>(R.id.etTelefono)?.text.toString()
                val azienda = dialogView.findViewById<EditText>(R.id.etAzienda)?.text.toString()
                val tipo = dialogView.findViewById<EditText>(R.id.etTipo)?.text.toString()

                val nuovoCliente = Cliente(
                    nome = nome,
                    cognome = cognome,
                    email = email,
                    password = password,
                    telefono = telefono,
                    azienda = azienda,
                    tipo = tipo
                )

                clienteWM.insert(nuovoCliente)

            }
            .setNegativeButton("Annulla", null)
            .create()

        dialog.show()
    }
}