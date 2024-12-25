package com.example.progetto_si

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ClienteDao {

    @Insert
    fun insertCliente(cliente : Cliente)


}