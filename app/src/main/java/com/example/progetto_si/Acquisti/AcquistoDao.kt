package com.example.progetto_si.Acquisti

import androidx.lifecycle.LiveData
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

    @Query ("SELECT p.nome  FROM acquisti a join pacchetto p on a.pacchetto=p.id  GROUP BY pacchetto ORDER BY COUNT(pacchetto) DESC LIMIT 2 ")
    fun maxPacchettoAcquistato() : List<String>

    @Query ("SELECT p.nome  FROM acquisti a join pacchetto p on a.pacchetto=p.id  WHERE cliente=:idCliente GROUP BY pacchetto ORDER BY COUNT(pacchetto) DESC LIMIT 2")
    fun maxPacchettoCliente(idCliente : Int) : List<String>

}