package com.example.progetto_si.Pacchetto

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.progetto_si.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PacchettoViewModel (application : Application) : AndroidViewModel(application) {

    private val pacchettoDao = MyDatabase.getDatabase(application).PacchettoDao()

    private val _pacchetti = MutableLiveData<List<Pacchetto>>()
    val pacchetti: LiveData<List<Pacchetto>> = _pacchetti

    fun insert(pacchetto : Pacchetto){
        viewModelScope.launch(Dispatchers.IO) {
            pacchettoDao.insertPacchetto(pacchetto)
        }
    }

    fun caricaPacchetti() {
        // Simulazione di caricamento pacchetti
        _pacchetti.value = listOf(
            Pacchetto(1, "Pacchetto Base", "pacchetto base",29.99, "30 giorni","Router di base","Antivirus standard"),
            Pacchetto(2, "Pacchetto Intermedio", "pacchetto intermedio", 49.99, "60 giorni","Router di base","Antivirus standard"),
            Pacchetto(3, "Pacchetto Avanzato", "pacchetto avanzato", 99.99, "90 giorni","Router di base","Antivirus standard")
        )
    }

    fun acquistaPacchetto(pacchetto: Pacchetto, username: String, callback: (Boolean) -> Unit) {
        // Simula un acquisto
        if (username.isNotEmpty()) {
            callback(true)
        } else {
            callback(false)
        }
    }

    fun getAllPacchetti(callback: (List<String>) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = pacchettoDao.getAllPacchettiS()
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
            var dettagli : List<String> = listOf(result.id.toString() , result.nome ,result.descrizione ,
                result.prezzo.toString() , result.durata , result.componenteHardware , result.componenteSoftware)
            withContext(Dispatchers.Main) {
                callback(dettagli)
            }
        }
    }
}
