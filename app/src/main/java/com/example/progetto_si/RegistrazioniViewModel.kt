package com.example.progetto_si

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrazioniViewModel(application : Application) : AndroidViewModel(application) {
    private val RegDao = MyDatabase.getDatabase(application).RegistrazioneDao()

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