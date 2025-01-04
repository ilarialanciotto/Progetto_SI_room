package com.example.progetto_si.Aquisti

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AcquistoDao {

    @Insert
    fun insertAcquisto(acquisto : Acquisti)

    @Query ("SELECT COUNT (DISTINCT cliente) FROM ACQUISTI WHERE pacchetto=:pacchetto")
    fun getNumCliePacch(pacchetto : Int) : Int
}