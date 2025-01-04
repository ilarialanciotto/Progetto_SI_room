package com.example.progetto_si.Pacchetto

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.progetto_si.Cliente.Room.Cliente

@Entity(
    tableName = "pacchetti_acquistati",
    primaryKeys = ["clienteId", "pacchettoId"],
    foreignKeys = [
        ForeignKey(
            entity = Cliente::class,
            parentColumns = ["id"],
            childColumns = ["clienteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Pacchetto::class,
            parentColumns = ["id"],
            childColumns = ["pacchettoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PacchettoAcquistato(
    val clienteId: Int,
    val pacchettoId: Int,
    val dataAcquisto: String  // es. "04-01-2025"
)
