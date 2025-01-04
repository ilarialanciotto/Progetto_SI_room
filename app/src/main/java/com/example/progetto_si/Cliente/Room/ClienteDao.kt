package com.example.progetto_si.Cliente.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.progetto_si.Pacchetto.Pacchetto

import androidx.room.*
import com.example.progetto_si.ClassiUtili.Coppia
import com.example.progetto_si.Pacchetto.PacchettoAcquistato

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

    @Query ("SELECT N.nota,C.email FROM CLIENTI C JOIN NOTE N ON C.email=N.email WHERE N.pacchetto=:pacchetto")
    fun getNotaClientePacchetto(pacchetto : Int) : List<Coppia>

    @Query("SELECT * FROM clienti")
    fun getAllClients(): List<Cliente>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun acquistaPacchetto(pacchettoAcquistato: PacchettoAcquistato)

    @Transaction
    @Query("SELECT * FROM clienti WHERE id = :clienteId")
    fun getPacchettiAcquistatiByCliente(clienteId: Int): List<ClienteConPacchetti>
}
