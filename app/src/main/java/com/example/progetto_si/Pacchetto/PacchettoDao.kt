package com.example.progetto_si.Pacchetto

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface PacchettoDao {

    @Query("SELECT * FROM pacchetto ORDER BY id ASC")
    fun getAllPacchetti(): Flow<List<Pacchetto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPacchetto(pacchetto: Pacchetto)

    @Query("DELETE FROM pacchetto")
    fun deleteAllPacchetti()

}
