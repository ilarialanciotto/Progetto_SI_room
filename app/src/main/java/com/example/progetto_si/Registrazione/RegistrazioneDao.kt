package com.example.progetto_si.Registrazione


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface RegistrazioneDao {

    @Insert
    fun insertRegistrazioni(registrazione : Registrazioni)

    @Query("SELECT username FROM registrazioni ")
    fun getAllnames(): List<String>

    @Query("SELECT COUNT(*) FROM registrazioni WHERE username=:user")
    fun checkReg(user: String): Int

    @Query("SELECT COUNT(*)  FROM registrazioni WHERE username = :user AND password = :pass")
    fun checkCredenziali(user: String, pass: String): Int

    @Query("SELECT username FROM registrazioni WHERE username LIKE '%' || :query || '%'")
    fun searchNames(query: String): List<String>
}