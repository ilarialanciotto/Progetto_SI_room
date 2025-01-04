package com.example.progetto_si

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.progetto_si.Cliente.Room.ClienteViewModel
import com.example.progetto_si.Login.Login
import com.example.progetto_si.Registrazione.Registrazioni
import com.example.progetto_si.Registrazione.RegistrazioniViewModel
import kotlinx.coroutines.launch

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var registrazioniViewModel = RegistrazioniViewModel(application)
        var ClienteViewModel = ClienteViewModel(application)

        val editTextName: EditText = findViewById(R.id.et_name)
        val editTextSurname: EditText = findViewById(R.id.et_surname)
        val editTextUsername: EditText = findViewById(R.id.et_usernameR)
        val editTextPassword: EditText = findViewById(R.id.et_PasswordR)
        val btnAggiungi: Button = findViewById(R.id.btn_registra)
        val btnLog: Button = findViewById(R.id.btn_login)

        btnAggiungi.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Inserire almeno username e password", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    registrazioniViewModel.checkReg(username) { isUserExist ->
                        if (isUserExist) {
                            Toast.makeText(this@Register, "Registrazione già esistente", Toast.LENGTH_SHORT).show()
                            btnLog.visibility = View.VISIBLE
                        } else {
                            val registrazione = Registrazioni(
                                nome = editTextName.text.toString().trim(),
                                cognome = editTextSurname.text.toString().trim(),
                                email = editTextUsername.text.toString().trim(),
                                password = editTextPassword.text.toString().trim()
                            )
                            registrazioniViewModel.insert(registrazione)
                            ClienteViewModel.insertCliente(
                                editTextName.text.toString().trim(),
                                editTextSurname.text.toString().trim(),
                                editTextUsername.text.toString().trim(),
                                editTextPassword.text.toString().trim(),
                                telefono = "",
                                azienda = "",
                                tipo = "cliente"
                            )

                            Toast.makeText(this@Register, "Dati inseriti con successo", Toast.LENGTH_SHORT).show()
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
