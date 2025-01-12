package com.example.progetto_si.Sviluppatore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.progetto_si.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SviluppatoreViewModel(application: Application) : AndroidViewModel(application) {

    // Istanzia il DAO da Room
    private val sviluppatoreDao = MyDatabase.getDatabase(application).SviluppatoreDao()

    fun insert(sviluppatore: Sviluppatore) {
        viewModelScope.launch(Dispatchers.IO) {
            sviluppatoreDao.insert(sviluppatore)
        }
    }

    fun update(sviluppatore: Sviluppatore) = viewModelScope.launch(Dispatchers.IO) {
        sviluppatoreDao.update(sviluppatore)
    }
    fun getAllSviluppatori(): List<Sviluppatore> {
        return sviluppatoreDao.getAllSviluppatori()
    }

    fun insertSviluppatore(nome: String, cognome: String, email: String, password: String, livello: String, progetti: String) {
        val sviluppatore = Sviluppatore(
            nome = nome,
            cognome = cognome,
            email = email,
            password = password,
            livello = livello,
            progetti = progetti,
            tipo = "sviluppatore"
        )
        viewModelScope.launch(Dispatchers.IO) {
            sviluppatoreDao.insert(sviluppatore)
        }
    }
}
