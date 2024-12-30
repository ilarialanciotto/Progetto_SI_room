package com.example.progetto_si.Sviluppatore

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sviluppatori")
data class Sviluppatore(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val cognome: String,
    val email: String, // Il nostro username
    val password: String,
    val livello: String, // Es. "junior", "senior", ecc.
    val progetti: String,
    val tipo: String
)
