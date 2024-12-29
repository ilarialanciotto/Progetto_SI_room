package com.example.progetto_si.Registrazione

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="registrazioni")
data class Registrazioni(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    val nome:String,
    val cognome: String,
    val email: String,
    val password: String
)
