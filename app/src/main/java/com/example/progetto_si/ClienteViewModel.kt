package com.example.progetto_si

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClienteViewModel(application : Application) : AndroidViewModel(application) {

    private val ClienteDao = MyDatabase.getDatabase(application).ClienteDao()

    fun insert(cliente: Cliente) {
        viewModelScope.launch (Dispatchers.IO){
            ClienteDao.insertCliente(cliente)
        }
    }
}