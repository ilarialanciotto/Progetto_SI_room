package com.example.progetto_si.Pacchetto

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PacchettoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pacchetto: Pacchetto)

    @Update
    suspend fun update(pacchetto: Pacchetto)

    @Delete
    suspend fun delete(pacchetto: Pacchetto)

    @Query("SELECT * FROM pacchetto ORDER BY nome ASC")
    fun getAllPacchetti(): LiveData<List<Pacchetto>>

    @Query("SELECT * FROM pacchetto WHERE id = :pacchettoId")
    suspend fun getPacchettoById(pacchettoId: Int): Pacchetto?
}
