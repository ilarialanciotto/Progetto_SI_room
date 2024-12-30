package com.example.progetto_si.Registrazione

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.progetto_si.Cliente.ClienteViewModel
import com.example.progetto_si.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrazioniViewModel(application : Application) : AndroidViewModel(application) {

    private val RegDao = MyDatabase.getDatabase(application).RegistrazioneDao()
    private val clienteDao = MyDatabase.getDatabase(application).ClienteDao()
    private val adminDao = MyDatabase.getDatabase(application).AdminDao()
    private val sviluppatoreDao = MyDatabase.getDatabase(application).SviluppatoreDao()

    fun checkCredenziali(user: String, password: String, callback: (Boolean, String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val isCliente = clienteDao.checkCliente(user, password) > 0
             val isAdmin = adminDao.checkAdmin(user, password) > 0
             val isSviluppatore = sviluppatoreDao.checkSviluppatore(user, password) > 0

            val userType = when {
                isCliente -> "cliente"
                isAdmin -> "admin"
                isSviluppatore -> "sviluppatore"
                else -> null
            }

            withContext(Dispatchers.Main) {
                callback(userType != null, userType)
            }
        }
    }


    fun insert(registrazione: Registrazioni) {
        viewModelScope.launch (Dispatchers.IO){
            RegDao.insertRegistrazioni(registrazione)
        }
    }

    fun checkReg(user: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val count = RegDao.checkReg(user)
            withContext(Dispatchers.Main) {
                callback(count > 0)
            }
        }
    }


}