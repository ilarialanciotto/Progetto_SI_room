package com.example.progetto_si.ClassiUtili

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "ordine")
data class Ordine(
    @PrimaryKey val id: Int=0,
    val clienteld: Int, // Relazione con Cliente
    val pacchettoId: Int, //ID del pacchetto associato
    val dataOrdine: String //Data dell'ordine
)
