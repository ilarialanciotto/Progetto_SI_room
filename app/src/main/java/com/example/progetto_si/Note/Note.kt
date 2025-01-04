package com.example.progetto_si.Note

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.progetto_si.Pacchetto.Pacchetto

@Entity(tableName="note")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    val data:String,
    val username: String,
    val nota: String,
    val pacchetto: Int
)


