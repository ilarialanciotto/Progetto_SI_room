package com.example.progetto_si

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RegistrazioneDao {

    @Insert
    fun insertRegistrazioni(registrazione : Registrazioni)

    @Update
    fun AggiornaRegistrazioni(registrazione : Registrazioni)

    @Query("SELECT * FROM registrazioni")
    fun getAllRegistrazioni(): LiveData<List<Registrazioni>>

    @Query("SELECT username FROM registrazioni ")
    fun getAllnames(): LiveData<List<String>>

    @Query("SELECT COUNT(*) FROM registrazioni WHERE username=:user")
    fun checkReg(user: String): Int

    @Query("SELECT COUNT(*)  FROM registrazioni WHERE username = :user AND password = :pass")
    fun checkCredenziali(user: String, pass: String): Int

    @Query("SELECT username FROM registrazioni WHERE username LIKE :query")
    fun searchNames(query: String): LiveData<List<String>>
}