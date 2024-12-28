package com.example.progetto_si.Auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.progetto_si.MyDatabase
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository: AuthRepository

    init {
        val db = MyDatabase.getDatabase(application)
        authRepository = AuthRepository(db.AdminDao(), db.ClienteDao(), db.SviluppatoreDao())
    }

    fun checkCredenziali(username: String, password: String, callback: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            if (authRepository.checkAdmin(username, password)) {
                callback(true, "admin")
            } else if (authRepository.checkCliente(username, password)) {
                callback(true, "cliente")
            } else if (authRepository.checkSviluppatore(username, password)) {
                callback(true, "sviluppatore")
            } else {
                callback(false, null) // Credenziali non valide
            }
        }
    }
}
