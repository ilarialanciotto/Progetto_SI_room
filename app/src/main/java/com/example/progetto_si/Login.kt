package com.example.progetto_si

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class Login : AppCompatActivity() {

    private var isPasswordVisible = false
    lateinit var editTextPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextUsername: EditText = findViewById(R.id.et_username)
        editTextPassword = findViewById(R.id.et_Password)
        val buttonSend: Button = findViewById(R.id.btn_invia)
        val dbHelper = MyDatabaseHelper(this)
        val db = dbHelper.readableDatabase

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
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            val isSuccess = dbHelper.checkCredenziali(username, password)
            if (isSuccess) {
                Toast.makeText(this, "Account valido", Toast.LENGTH_SHORT).show()
                dbHelper.close()
                val intent = Intent(this, LogActivity::class.java)
                intent.putExtra("EXTRA_USERNAME", username)
                intent.putExtra("EXTRA_PASSWORD", password)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Username o password errati", Toast.LENGTH_SHORT).show()
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