package com.example.progetto_si.Cliente.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.progetto_si.Cliente.Room.Cliente
import com.example.progetto_si.Cliente.Room.ClienteViewModel
import com.example.progetto_si.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GestioneDatiActivity : AppCompatActivity() {

    private lateinit var clienteViewModel: ClienteViewModel
    private lateinit var editName: EditText
    private lateinit var editSurname: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var editPhone: EditText
    private lateinit var editCompany: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonUndo: Button

    private var clienteCorrente: Cliente? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestione_dati)

        clienteViewModel = ViewModelProvider(this)[ClienteViewModel::class.java]

        // Inizializza le view
        editName = findViewById(R.id.editName)
        editSurname = findViewById(R.id.editSurname)
        editEmail = findViewById(R.id.editEmailAddress)
        editPassword = findViewById(R.id.editPassword)
        editPhone = findViewById(R.id.editTextPhone)
        editCompany = findViewById(R.id.editCompany)
        buttonSave = findViewById(R.id.buttonSave)
        buttonUndo = findViewById(R.id.buttonUndo)


        val email = intent.getStringExtra("EXTRA_USERNAME")

        if (email != null) {
            caricaDatiCliente(email)
        } else {
            Toast.makeText(this, "Errore: Email non trovata", Toast.LENGTH_SHORT).show()
        }


        // Salva le modifiche al database
        buttonSave.setOnClickListener {
            aggiornaDatiCliente()
        }

        // Ripristina i dati originali se premuto "Annulla"
        buttonUndo.setOnClickListener {
            ripristinaDatiOriginali()
        }


    }

    private fun caricaDatiCliente(email: String) {
        lifecycleScope.launch {
            val cliente = withContext(Dispatchers.IO) { clienteViewModel.getClienteByEmail(email) }

            if (cliente != null) {
                clienteCorrente = cliente

                withContext(Dispatchers.Main) {
                    editName.setText(cliente.nome)
                    editSurname.setText(cliente.cognome)
                    editEmail.setText(cliente.email)
                    editEmail.isEnabled = false
                    editPassword.setText(cliente.password)
                    editPhone.setText(cliente.telefono)
                    editCompany.setText(cliente.azienda)
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@GestioneDatiActivity, "Errore: Utente non trovato", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun aggiornaDatiCliente() {
        val cliente = clienteCorrente
        if (cliente == null) {
            Toast.makeText(this, "Errore: Nessun cliente caricato", Toast.LENGTH_SHORT).show()
            return
        }

        val clienteAggiornato = Cliente(
            cliente.id, // Mantieni lo stesso ID
            editName.text.toString(),
            editSurname.text.toString(),
            editEmail.text.toString(),
            editPassword.text.toString(),
            editPhone.text.toString(),
            editCompany.text.toString(),
            "cliente"
        )

        lifecycleScope.launch {
            clienteViewModel.updateCliente(clienteAggiornato)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@GestioneDatiActivity, "Dati aggiornati con successo", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun ripristinaDatiOriginali() {
        clienteCorrente?.let {
            editName.setText(it.nome)
            editSurname.setText(it.cognome)
            editEmail.setText(it.email)
            editPassword.setText(it.password)
            editPhone.setText(it.telefono)
            editCompany.setText(it.azienda)
        } ?: Toast.makeText(this, "Nessun dato da ripristinare", Toast.LENGTH_SHORT).show()
    }
}
