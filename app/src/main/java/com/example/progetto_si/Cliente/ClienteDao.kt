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

    @Query("SELECT * FROM clienti WHERE email = :clienteEmail AND password = :clientepassword")
    fun isCliente(clienteEmail: String, clientepassword: String): Boolean

    @Query("SELECT COUNT(*) > 0 FROM clienti WHERE email = :clienteEmail")
    fun isClienteExists(clienteEmail: String): Boolean

    @Insert
    fun insertAll(vararg clienti: Cliente)

}
