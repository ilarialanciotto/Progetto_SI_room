package com.example.progetto_si.Acquisti

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="acquisti")
data class Acquisti(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cliente: Int,
    val pacchetto: Int,
    val quantità: Int
)