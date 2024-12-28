package com.example.progetto_si.Sviluppatore

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SviluppatoreDao {
    @Query("SELECT COUNT(*) > 0 FROM sviluppatori WHERE username = :username AND password = :password")
    fun isSviluppatore(username: String, password: String): Boolean

    @Query("SELECT * FROM sviluppatori WHERE username = :username")
    fun getSviluppatoreByUsername(username: String): Sviluppatore?

    @Insert
    fun insertAll(vararg sviluppatori: Sviluppatore)
}
