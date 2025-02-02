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

    private lateinit var clienteCorrente: Cliente

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

        val email = intent.getStringExtra("email")

        if (email != null) {
            caricaDatiCliente(email)
        } else {
            Toast.makeText(this, "Errore: Nessuna email trovata", Toast.LENGTH_SHORT).show()
            finish()  // Chiude l'activity se l'email non è disponibile
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
            cliente?.let {
                clienteCorrente = clienteViewModel.getClienteByEmail(email)!!
                runOnUiThread {
                    editName.setText(it.nome)
                    editSurname.setText(it.cognome)
                    editEmail.setText(it.email)  // Email non modificabile
                    editEmail.isEnabled = false
                    editPassword.setText(it.password)
                    editPhone.setText(it.telefono)
                    editCompany.setText(it.azienda)
                }
            } ?: runOnUiThread {
                Toast.makeText(this@GestioneDatiActivity, "Errore: Utente non trovato", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun aggiornaDatiCliente() {
        if (clienteCorrente == null) {
            Toast.makeText(this, "Errore: Nessun utente caricato", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedCliente = Cliente(
            email = editEmail.text.toString(),
            nome = editName.text.toString(),
            cognome = editSurname.text.toString(),
            password = editPassword.text.toString(),
            telefono = editPhone.text.toString(),
            azienda = editCompany.text.toString(),
            tipo = "cliente"
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                clienteViewModel.updateCliente(updatedCliente)
            }
            runOnUiThread {
                Toast.makeText(this@GestioneDatiActivity, "Dati aggiornati con successo!", Toast.LENGTH_SHORT).show()
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
