package com.example.progetto_si.Cliente

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.progetto_si.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClienteViewModel(application: Application) : AndroidViewModel(application) {

    // Istanzia il DAO da Room
    private val clienteDao = MyDatabase.getDatabase(application).ClienteDao()

    // Metodo per inserire un cliente nel database
    fun insert(cliente: Cliente) {
        viewModelScope.launch(Dispatchers.IO) {
            clienteDao.insertCliente(cliente)
        }
    }
}
