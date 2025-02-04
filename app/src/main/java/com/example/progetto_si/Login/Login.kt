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
import androidx.lifecycle.lifecycleScope
import com.example.progetto_si.R
import com.example.progetto_si.Registrazione.RegistrazioniViewModel
import kotlinx.coroutines.launch


class Login : AppCompatActivity() {

    private var isPasswordVisible = false
    lateinit var editTextPassword: EditText

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextUsername: EditText = findViewById(R.id.et_username)
        editTextPassword = findViewById(R.id.et_Password)
        val buttonSend: Button = findViewById(R.id.btn_invia)
        var registrazioniViewModel = RegistrazioniViewModel(application)

        editTextPassword.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = editTextPassword.compoundDrawables[2]
                if (drawableEnd != null) {
                    val drawableWidth = drawableEnd.bounds.width()
                    val touchX = event.rawX

                    val editTextRightEdge = editTextPassword.right - drawableWidth
                    if (touchX >= editTextRightEdge) {
                        togglePasswordVisibility()
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }

        buttonSend.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            lifecycleScope.launch {
                registrazioniViewModel.checkCredenziali(username, password) { isSuccess, userType ->
                    if (isSuccess && userType != null) {
                        when (userType) {
                            "cliente" -> {
                                val intent = Intent(this@Login, DashboardCliente::class.java)
                                intent.putExtra("EXTRA_USERNAME", username)
                                intent.putExtra("EXTRA_PASSWORD", password)
                                startActivity(intent)
                            }
                            "admin" -> {
                                val intent = Intent(this@Login, DashboardAdmin::class.java)
                                intent.putExtra("EXTRA_USERNAME", username)
                                startActivity(intent)
                            }
                            "sviluppatore" -> {
                                val intent = Intent(this@Login, DashboardSviluppatore::class.java)
                                intent.putExtra("EXTRA_USERNAME", username)
                                startActivity(intent)
                            }
                            else -> {
                                Toast.makeText(this@Login,
                                    getString(R.string.tipo_di_utente_sconosciuto), Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@Login,
                            getString(R.string.username_o_password_errati), Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            editTextPassword.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                ContextCompat.getDrawable(this, R.drawable.eyeon),
                null
            )
        } else {
            editTextPassword.inputType = InputType.TYPE_CLASS_TEXT
            editTextPassword.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                ContextCompat.getDrawable(this, R.drawable.eyeoff),
                null
            )
        }
        editTextPassword.setSelection(editTextPassword.text.length)
        isPasswordVisible = !isPasswordVisible
    }


}