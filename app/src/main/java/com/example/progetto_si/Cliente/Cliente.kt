package com.example.progetto_si.Cliente

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName="clienti")
data class Cliente(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    val nome:String,
    val cognome: String,
    val email: String, //il nostro username
    val password: String,
    val telefono: String,
    val azienda : String,
    val tipo: String
)
