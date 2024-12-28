package com.example.progetto_si.Sviluppatore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.progetto_si.MyDatabase
import kotlinx.coroutines.launch

class SviluppatoreViewModel(application: Application) : AndroidViewModel(application) {

    private val sviluppatoreDao: SviluppatoreDao

    init {
        val db = MyDatabase.getDatabase(application)
        sviluppatoreDao = db.SviluppatoreDao()
    }

    fun checkCredenziali(username: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isValid = sviluppatoreDao.isSviluppatore(username, password)
            callback(isValid)
        }
    }

    fun getSviluppatore(username: String, callback: (Sviluppatore?) -> Unit) {
        viewModelScope.launch {
            val sviluppatore = sviluppatoreDao.getSviluppatoreByUsername(username)
            callback(sviluppatore)
        }
    }
}
