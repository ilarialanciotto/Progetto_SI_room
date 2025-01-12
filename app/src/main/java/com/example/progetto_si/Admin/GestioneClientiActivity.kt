package com.example.progetto_si.Admin

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.ClassiUtili.ClienteAdapter
import com.example.progetto_si.Cliente.Room.Cliente
import com.example.progetto_si.Cliente.Room.ClienteDao
import com.example.progetto_si.Cliente.Room.ClienteViewModel
import com.example.progetto_si.MyDatabase
import com.example.progetto_si.R
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer

class GestioneClientiActivity : AppCompatActivity() {
    private lateinit var viewModel: ClienteViewModel
    private lateinit var clienteAdapter: ClienteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestione_clienti)

        // Configura l'adattatore per la RecyclerView
        clienteAdapter = ClienteAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_clienti)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configura il ViewModel per osservare i dati
        viewModel = ViewModelProvider(this).get(ClienteViewModel::class.java)
        recyclerView.adapter = clienteAdapter
        viewModel.getClientiConPacchetti().observe(this) { clienti ->
            clienteAdapter.submitList(clienti)
        }

        // Ottieni un'istanza del database Room
        val database = MyDatabase.getDatabase(this)
        val clienteDao = database.ClienteDao()

        // Configura il bottone per aggiungere un cliente
        val btnAggiungiCliente = findViewById<Button>(R.id.btnAggiungiCliente)
        btnAggiungiCliente.setOnClickListener {
            // Mostra il dialog per aggiungere un cliente
            showAddClienteDialog(clienteDao)
        }
    }

    // Metodo per mostrare il dialog per aggiungere un cliente
    private fun showAddClienteDialog(clienteDao: ClienteDao) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_cliente, null)
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
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

                // Inserisci il cliente nel database in un thread separato
                Thread {
                    clienteDao.insertCliente(nuovoCliente)
                }.start()
            }
            .setNegativeButton("Annulla", null)
            .create()

        dialog.show()
    }
}