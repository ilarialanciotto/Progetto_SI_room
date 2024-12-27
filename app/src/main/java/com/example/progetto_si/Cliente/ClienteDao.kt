package com.example.progetto_si.Cliente

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ClienteDao {

    @Insert
    fun insertCliente(cliente : Cliente)

}