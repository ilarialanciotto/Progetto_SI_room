package com.example.progetto_si.Login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.progetto_si.Auth.AuthViewModel
import com.example.progetto_si.R
import com.example.progetto_si.Registrazione.RegistrazioniViewModel
import kotlinx.coroutines.launch


class Login : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel
    private var isPasswordVisible = false
    lateinit var editTextPassword: EditText

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        val editTextUsername: EditText = findViewById(R.id.et_username)
        editTextPassword = findViewById(R.id.et_Password)
        val buttonSend: Button = findViewById(R.id.btn_invia)

        buttonSend.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            authViewModel.checkCredenziali(username, password) { isSuccess, role ->
                if (isSuccess) {
                    when (role) {
                        "admin" -> navigateToLoginAdmin(username, password)
                        "cliente" -> navigateToLoginCliente(username, password)
                        "sviluppatore" -> navigateToLoginSviluppatore(username, password)
                        else -> Toast.makeText(this@Login, "Ruolo non riconosciuto", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@Login, "Username o password errati", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToLoginAdmin(username: String, password: String) {
        val intent = Intent(this, LoginAmministratore::class.java)
        intent.putExtra("EXTRA_USERNAME", username)
        intent.putExtra("EXTRA_PASSWORD", password)
        startActivity(intent)
    }

    private fun navigateToLoginCliente(username: String, password: String) {
        val intent = Intent(this, LoginCliente::class.java)
        intent.putExtra("EXTRA_USERNAME", username)
        intent.putExtra("EXTRA_PASSWORD", password)
        startActivity(intent)
    }

    private fun navigateToLoginSviluppatore(username: String, password: String) {
        val intent = Intent(this, LoginSviluppatore::class.java)
        intent.putExtra("EXTRA_USERNAME", username)
        intent.putExtra("EXTRA_PASSWORD", password)
        startActivity(intent)
    }
}
