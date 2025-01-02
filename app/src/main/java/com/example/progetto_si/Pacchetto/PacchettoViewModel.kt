package com.example.progetto_si.Pacchetto

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.progetto_si.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PacchettoViewModel (application : Application) : AndroidViewModel(application) {

    private val pacchettoDao = MyDatabase.getDatabase(application).PacchettoDao()

    fun insert(pacchetto : Pacchetto){
        viewModelScope.launch(Dispatchers.IO) {
            pacchettoDao.insertPacchetto(pacchetto)
        }
    }

    fun getAllPacchetti(callback: (List<String>) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = pacchettoDao.getAllPacchetti()
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    fun getAllid(callback: (List<Int>) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = pacchettoDao.getAllId()
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    fun Aggiorna(pack : Pacchetto){
        viewModelScope.launch(Dispatchers.IO) {
            pacchettoDao.aggiorna(pack)
        }
    }

    fun getDettaggliPacchetto(nome : String , callback: (List<String>) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = pacchettoDao.getDettaggliPacchetto(nome)
            var dettagli : List<String> = listOf(
                result.id.toString(), result.nome, result.prezzo.toString(),
                result.dettagli, result.componenteHardware, result.componenteSoftware
            )
            withContext(Dispatchers.Main) {
                callback(dettagli)
            }
        }
    }

}