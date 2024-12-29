package com.example.progetto_si.Registrazione


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface RegistrazioneDao {

    @Insert
    fun insertRegistrazioni(registrazione : Registrazioni)

    @Query("SELECT email FROM registrazioni ")
    fun getAllnames(): List<String>

    @Query("SELECT COUNT(*) FROM registrazioni WHERE email= :user")
    fun checkReg(user: String): Int

    @Query("SELECT COUNT(*)  FROM registrazioni WHERE email = :user AND password = :pass")
    fun checkCredenziali(user: String, pass: String): Int

    @Query("SELECT email FROM registrazioni WHERE email LIKE '%' || :query || '%'")
    fun searchNames(query: String): List<String>
}