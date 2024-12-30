package com.example.progetto_si.Admin

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admin")
data class Admin(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val cognome: String,
    val email: String, // Il nostro username
    val password: String,
    val ruolo: String // Es. "superadmin", "gestore", ecc.
)
