package com.example.progetto_si.Pacchetto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="pacchetto")
data class Pacchetto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val dettagli: String,
    val prezzo: Double,
    val componenteHardware: String,
    val componenteSoftware: String
)
