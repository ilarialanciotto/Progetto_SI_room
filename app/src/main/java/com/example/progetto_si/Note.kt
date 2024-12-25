package com.example.progetto_si

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="note")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    val data:String,
    val username: String,
    val nota: String
)


