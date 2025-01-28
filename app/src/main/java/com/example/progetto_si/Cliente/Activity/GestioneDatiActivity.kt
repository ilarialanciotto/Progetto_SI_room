package com.example.progetto_si.Cliente.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.progetto_si.Cliente.Room.Cliente
import com.example.progetto_si.Cliente.Room.ClienteDao
import com.example.progetto_si.Cliente.Room.ClienteViewModel
import com.example.progetto_si.MyDatabase
import com.example.progetto_si.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GestioneDatiActivity : AppCompatActivity() {

    private lateinit var clienteViewModel: ClienteViewModel
    private lateinit var clienteDao: ClienteDao
    private lateinit var editTextNome: EditText
    private lateinit var editTextCognome: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextTelefono: EditText
    private lateinit var editTextAzienda: EditText
    private lateinit var btnSalva: Button
    private lateinit var btnAnnulla: Button
    private lateinit var passwordUtente: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestione_dati)

        // Inizializza ViewModel
        clienteViewModel = ViewModelProvider(this)[ClienteViewModel::class.java]
        clienteDao = MyDatabase.getDatabase(this).ClienteDao()

        // Recupera i riferimenti delle viste
        editTextNome = findViewById(R.id.editTextNome)
        editTextCognome = findViewById(R.id.editTextCognome)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextTelefono = findViewById(R.id.editTextTelefono)
        editTextAzienda = findViewById(R.id.editTextAzienda)
        btnSalva = findViewById(R.id.btnSalva)
        btnAnnulla = findViewById(R.id.btnAnnulla)

        // Recupera dati utente
        val username = intent.getStringExtra("EXTRA_USERNAME") ?: ""
        val password = intent.getStringExtra("EXTRA_PASSWORD") ?: ""

        // Carica i dati del cliente
        loadClienteData(username)

        // Listener per il pulsante "Salva"
        btnSalva.setOnClickListener {
            salvaDatiCliente(username)
        }

        // Listener per il pulsante "Annulla"
        btnAnnulla.setOnClickListener {
            finish() // Chiude l'attività senza salvare
        }
    }

    private fun loadClienteData(username: String) {
        // Usa coroutines per l'accesso asincrono al database
        lifecycleScope.launch {
            // Spostiamo l'operazione di lettura del database su un thread di background (IO)
            val cliente = withContext(Dispatchers.IO) {
                clienteDao.getClienteByEmailAndPassword(username, passwordUtente)
            }

            cliente?.let {
                editTextNome.setText(it.nome)
                editTextCognome.setText(it.cognome)
                editTextEmail.setText(it.email)
                editTextTelefono.setText(it.telefono)
            } ?: run {
                Toast.makeText(this@GestioneDatiActivity, "Cliente non trovato", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }


    private fun salvaDatiCliente(username: String) {
        val nuovoNome = editTextNome.text.toString()
        val nuovoCognome = editTextCognome.text.toString()
        val nuovaEmail = editTextEmail.text.toString()
        val nuovaPassword = editTextPassword.text.toString()
        val nuovoTelefono = editTextTelefono.text.toString()
        val nuovaAzienda = editTextAzienda.text.toString()

        // Validazione dei campi
        if (nuovoNome.isEmpty() || nuovoCognome.isEmpty() || nuovaEmail.isEmpty() || nuovoTelefono.isEmpty()) {
            Toast.makeText(this, "Compila tutti i campi", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidEmail(nuovaEmail)) {
            Toast.makeText(this, "Email non valida", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidPhone(nuovoTelefono)) {
            Toast.makeText(this, "Numero di telefono non valido", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            // Recupera il cliente dal database in modo asincrono
            val cliente = clienteDao.getClienteByEmailAndPassword(username, passwordUtente)
            cliente?.let {
                val azienda = if (nuovaAzienda.isNotEmpty()) nuovaAzienda else it.azienda
                val passwordAggiornata = if (nuovaPassword.isNotEmpty()) nuovaPassword else it.password

                val clienteAggiornato = Cliente(
                    id = it.id,
                    email = it.email,
                    password = passwordAggiornata,  // aggiorna la password
                    nome = nuovoNome,
                    cognome = nuovoCognome,
                    telefono = nuovoTelefono,
                    azienda = azienda,  // aggiorna l'azienda
                    tipo = "cliente"
                )

                clienteViewModel.update(clienteAggiornato)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@GestioneDatiActivity, "Dati aggiornati con successo", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } ?: run {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@GestioneDatiActivity, "Errore durante l'aggiornamento", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPhone(phone: String): Boolean {
        return phone.length >= 10 // Assumiamo una lunghezza minima per il telefono
    }
}
