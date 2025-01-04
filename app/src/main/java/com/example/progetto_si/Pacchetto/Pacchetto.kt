package com.example.progetto_si.Pacchetto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pacchetto")
data class Pacchetto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val descrizione: String,
    val prezzo: Double,
    val durata: String,
    val componenteHardware: String,
    val componenteSoftware: String
)
