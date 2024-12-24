package com.example.progetto_si

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="registrazioni")
data class Registrazioni(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    val nome:String,
    val cognome: String,
    val eta: Int,
    val username: String,
    val password: String
)
