package com.example.progetto_si.Cliente.Room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.progetto_si.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClienteViewModel(application: Application) : AndroidViewModel(application) {

    // Istanzia il DAO da Room
    private val clienteDao = MyDatabase.getDatabase(application).ClienteDao()


    fun insert(cliente: Cliente) {
        viewModelScope.launch(Dispatchers.IO) {
            clienteDao.insert(cliente)
        }
    }

    fun insertCliente(nome: String, cognome: String, email: String, password: String, telefono: String, azienda: String, tipo: String) {
        val cliente = Cliente(
            nome = nome,
            cognome = cognome,
            email = email,
            password = password,
            telefono = telefono,
            azienda = azienda,
            tipo = tipo
        )
        viewModelScope.launch(Dispatchers.IO) {
            clienteDao.insert(cliente)
        }
    }

    fun getClienteByEmailAndPassword(email: String, password: String): Cliente? {
        return clienteDao.getClienteByEmailAndPassword(email, password)
    }


}
