package com.example.progetto_si.Note

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="note")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    val data:String,
    val email: String,
    val nota: String,
    val risposta: String? = null, // Risposta opzionale per l'assistenza
    val stato: String = "In attesa", // Stato della richiesta
    val pacchetto : Int
)


