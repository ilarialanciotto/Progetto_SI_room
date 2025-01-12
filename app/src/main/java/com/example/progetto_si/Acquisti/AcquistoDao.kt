package com.example.progetto_si.Acquisti

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.progetto_si.Pacchetto.Pacchetto

@Dao
interface AcquistoDao {

    @Insert
    fun insertAcquisto(acquisto : Acquisti)

    @Query("SELECT pacchetto FROM ACQUISTI WHERE cliente=:id")
    fun getPacchettiAcquistati(id : Int) : List<Int>

    @Query ("SELECT COUNT (DISTINCT cliente) FROM ACQUISTI WHERE pacchetto=:pacchetto")
    fun getNumCliePacch(pacchetto : Int) : Int

    @Query("SELECT pacchetto.* FROM acquisti JOIN pacchetto ON pacchetto.id = acquisti.pacchetto WHERE acquisti.cliente = :idCliente")
    fun getPacchettoById(idCliente: Int): List<Pacchetto>

    @Query ("SELECT COUNT (DISTINCT pacchetto) FROM ACQUISTI WHERE cliente=:idC")
    fun getNumeroAcquistiPerCliente(idC: Int): Int
}