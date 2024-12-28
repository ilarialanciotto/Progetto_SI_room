package com.example.progetto_si.Pacchetto

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.progetto_si.MyDatabase
import kotlinx.coroutines.launch
//
//class PacchettoViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val pacchettoDao: PacchettoDao
//    val allPacchetti: LiveData<List<Pacchetto>>
//
//    init {
//        val database = MyDatabase.getDatabase(application)
//        pacchettoDao = database.PacchettoDao()
//        allPacchetti = pacchettoDao.getAllPacchetti()
//    }
//
//    fun insert(pacchetto: Pacchetto) = viewModelScope.launch {
//        pacchettoDao.insert(pacchetto)
//    }
//
//    fun update(pacchetto: Pacchetto) = viewModelScope.launch {
//        pacchettoDao.update(pacchetto)
//    }
//
//    fun delete(pacchetto: Pacchetto) = viewModelScope.launch {
//        pacchettoDao.delete(pacchetto)
//    }
//}
