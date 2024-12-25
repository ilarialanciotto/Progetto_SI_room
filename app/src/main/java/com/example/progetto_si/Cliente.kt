package com.example.progetto_si

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="clienti")
data class Cliente(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    val nome:String,
    val email: String,
    val telefono: String,
    val azienda : String

)
