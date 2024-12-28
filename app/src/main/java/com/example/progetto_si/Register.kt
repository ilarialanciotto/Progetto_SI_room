package com.example.progetto_si

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.progetto_si.Cliente.Cliente
import com.example.progetto_si.Login.Login
import com.example.progetto_si.Login.LoginCliente
import com.example.progetto_si.Registrazione.Registrazioni
import com.example.progetto_si.Registrazione.RegistrazioniViewModel
import kotlinx.coroutines.launch
import com.example.progetto_si.Cliente.ClienteViewModel
import com.example.progetto_si.R


class Register : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val clienteViewModel = ClienteViewModel(application)

        val editTextName: EditText = findViewById(R.id.et_name)
        val editTextSurname: EditText = findViewById(R.id.et_surname)
        val editTextUsername: EditText = findViewById(R.id.et_emailR)
        val editTextPassword: EditText = findViewById(R.id.et_PasswordR)
        val btnAggiungi: Button = findViewById(R.id.btn_registra)
        val btnLog: Button = findViewById(R.id.btn_login)

        btnAggiungi.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Inserire almeno email e password", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    clienteViewModel.checkCliente(username) { isUserExist ->
                        if (isUserExist) {
                            Toast.makeText(this@Register, "L'utente esiste già", Toast.LENGTH_SHORT).show()
                            btnLog.visibility = View.VISIBLE
                        } else {
                            val cliente = Cliente(
                                nome = editTextName.text.toString(),
                                email = editTextUsername.text.toString(),
                                password = editTextPassword.text.toString(),
                                telefono = "",
                                azienda = ""
                            )
                            clienteViewModel.insert(cliente)
                            Toast.makeText(this@Register, "Cliente registrato con successo", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        btnLog.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}
