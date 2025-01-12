package com.example.progetto_si.Cliente.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.progetto_si.ClassiUtili.Coppia


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

    @Query("""
    WITH PacchettiAcquistati AS (
        SELECT clienteld, COUNT(*) AS numeroPacchetti
        FROM Ordine
        GROUP BY clienteld
    )
    SELECT 
        c.id AS clienteld, 
        c.nome, 
        c.email, 
        COALESCE(pa.numeroPacchetti, 0) AS numeroPacchetti
    FROM clienti c
    LEFT JOIN PacchettiAcquistati pa ON c.id = pa.clienteld
    ORDER BY c.nome ASC
""")
    fun getClientiConPacchetti(): LiveData<List<ClienteConPacchetti>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCliente(cliente: Cliente)
}
