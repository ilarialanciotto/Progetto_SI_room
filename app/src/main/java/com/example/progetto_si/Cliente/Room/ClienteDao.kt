package com.example.progetto_si.Cliente.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.progetto_si.Pacchetto.Pacchetto

@Dao
interface ClienteDao {

    @Insert
    fun insert(cliente: Cliente)

    @Query("SELECT * FROM clienti WHERE id = :clienteId")
    fun getClienteById(clienteId: Int): Cliente

    @Query("SELECT * FROM clienti WHERE email = :email AND password = :password")
    fun getClienteByEmailAndPassword(email: String, password: String): Cliente?

    @Query("SELECT COUNT(*) FROM clienti WHERE email = :email AND password = :password")
    fun checkCliente(email: String, password: String): Int

    @Query("SELECT * FROM clienti")
    fun getAllClients(): List<Cliente>

    @Query("INSERT INTO pacchetto (id, id) VALUES (:clienteId, :pacchettoId)")
    fun acquistaPacchetto(clienteId: Int, pacchettoId: Int)


}
