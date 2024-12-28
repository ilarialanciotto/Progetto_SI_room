package com.example.progetto_si.Sviluppatore

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sviluppatori")
data class Sviluppatore(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String
)
