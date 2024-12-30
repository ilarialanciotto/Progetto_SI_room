package com.example.progetto_si.Sviluppatore

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SviluppatoreDao {

    @Insert
    fun insert(sviluppatore: Sviluppatore)

    @Query("SELECT * FROM sviluppatori WHERE id = :sviluppatoreId")
    fun getSviluppatoreById(sviluppatoreId: Int): Sviluppatore

    @Query("SELECT * FROM sviluppatori WHERE email = :email")
    fun getSviluppatoreByEmail(email: String): Sviluppatore?

    @Query("SELECT * FROM sviluppatori WHERE tipo = :tipo")
    fun getSviluppatoriByTipo(tipo: String): List<Sviluppatore>

    @Query("SELECT COUNT(*) FROM sviluppatori WHERE email = :email AND password = :password")
    fun checkSviluppatore(email: String, password: String): Int

    @Query("SELECT * FROM sviluppatori")
    fun getAllSviluppatori(): List<Sviluppatore>
}


