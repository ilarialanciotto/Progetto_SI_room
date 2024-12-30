package com.example.progetto_si.Admin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.progetto_si.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminViewModel(application: Application) : AndroidViewModel(application) {

    // Istanzia il DAO da Room
    private val adminDao = MyDatabase.getDatabase(application).AdminDao()

    fun insert(admin: Admin) {
        viewModelScope.launch(Dispatchers.IO) {
            adminDao.insert(admin)
        }
    }

    fun insertAdmin(nome: String, cognome: String, email: String, password: String, ruolo: String) {
        val admin = Admin(
            nome = nome,
            cognome = cognome,
            email = email,
            password = password,
            ruolo = ruolo
        )
        viewModelScope.launch(Dispatchers.IO) {
            adminDao.insert(admin)
        }
    }

}
