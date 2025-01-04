package com.example.progetto_si.Cliente.Room

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.progetto_si.Pacchetto.Pacchetto
import com.example.progetto_si.Pacchetto.PacchettoAcquistato

data class ClienteConPacchetti(
    @Embedded val cliente: Cliente,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = PacchettoAcquistato::class,
            parentColumn = "clienteId",
            entityColumn = "pacchettoId"
        )
    )
    val pacchetti: List<Pacchetto>
)
