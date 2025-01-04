package com.example.progetto_si.Pacchetto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PacchettoViewModel : ViewModel() {

    private val _pacchetti = MutableLiveData<List<Pacchetto>>()
    val pacchetti: LiveData<List<Pacchetto>> = _pacchetti

    fun caricaPacchetti() {
        // Simulazione di caricamento pacchetti
        _pacchetti.value = listOf(
            Pacchetto(1, "Pacchetto Base", "pacchetto base",29.99, "30 giorni"),
            Pacchetto(2, "Pacchetto Intermedio", "pacchetto intermedio", 49.99, "60 giorni"),
            Pacchetto(3, "Pacchetto Avanzato", "pacchetto avanzato", 99.99, "90 giorni")
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
}
