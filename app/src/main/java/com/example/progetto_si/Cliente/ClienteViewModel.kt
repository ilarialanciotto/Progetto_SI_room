package com.example.progetto_si.Cliente

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.progetto_si.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClienteViewModel(application : Application) : AndroidViewModel(application) {

    private val ClienteDao = MyDatabase.getDatabase(application).ClienteDao()

    fun insert(cliente: Cliente) {
        viewModelScope.launch (Dispatchers.IO){
            ClienteDao.insertCliente(cliente)
        }
    }
}