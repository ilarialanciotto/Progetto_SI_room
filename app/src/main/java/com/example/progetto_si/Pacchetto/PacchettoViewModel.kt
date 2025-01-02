package com.example.progetto_si.Pacchetto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PacchettoViewModel(private val pacchettoDao: PacchettoDao) : ViewModel() {

    val allPacchetti: Flow<List<Pacchetto>> = pacchettoDao.getAllPacchetti()

    fun insertPacchetto(pacchetto: Pacchetto) {
        viewModelScope.launch {
            pacchettoDao.insertPacchetto(pacchetto)
        }
    }

    fun deleteAllPacchetti() {
        viewModelScope.launch {
            pacchettoDao.deleteAllPacchetti()
        }
    }
}

