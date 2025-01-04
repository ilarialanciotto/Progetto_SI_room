package com.example.progetto_si.Pacchetto

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface PacchettoDao {

    @Query("SELECT * FROM pacchetto ORDER BY id ASC")
    fun getAllPacchetti(): Flow<List<Pacchetto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPacchetto(pacchetto: Pacchetto)

    @Query("DELETE FROM pacchetto")
    fun deleteAllPacchetti()

    @Update
    fun aggiorna(pacchetto: Pacchetto)

    @Query("SELECT nome FROM pacchetto ORDER BY id ASC")
    fun getAllPacchettiS() : List<String>

    @Query ("SELECT id FROM pacchetto ORDER BY id ASC")
    fun getAllId(): List<Int>

    @Query("SELECT * FROM pacchetto WHERE nome=:nome LIMIT 1")
    fun getDettaggliPacchetto(nome : String) : Pacchetto
}
