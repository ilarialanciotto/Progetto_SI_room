package com.example.progetto_si.Cliente

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName="clienti")
data class Cliente(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    val nome:String,
    val email: String, //il nostro username
    val telefono: String,
    val azienda : String,
)
