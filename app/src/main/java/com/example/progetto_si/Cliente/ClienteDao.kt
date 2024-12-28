package com.example.progetto_si.Cliente

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ClienteDao {

    @Insert
    fun insertCliente(cliente: Cliente)


    @Query("SELECT * FROM clienti WHERE id = :clienteId")
    fun getClienteById(clienteId: Int): Cliente

}
