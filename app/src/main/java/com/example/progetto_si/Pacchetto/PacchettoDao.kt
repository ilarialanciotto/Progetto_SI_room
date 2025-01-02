package com.example.progetto_si.Pacchetto

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PacchettoDao {

    @Insert
    fun insertPacchetto(pacchetto : Pacchetto)

    @Update
    fun aggiorna(pacchetto: Pacchetto)

    @Query("SELECT nome FROM pacchetto ORDER BY id ASC")
    fun getAllPacchetti() : List<String>

    @Query ("SELECT id FROM pacchetto ORDER BY id ASC")
    fun getAllId(): List<Int>

    @Query("SELECT * FROM pacchetto WHERE nome=:nome LIMIT 1")
    fun getDettaggliPacchetto(nome : String) : Pacchetto

}