package com.example.progetto_si

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Registrazioni::class],version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase() {

    abstract fun RegistrazioneDao() : RegistrazioneDao

    companion object{
        @Volatile
        private var INSTANCE : MyDatabase? = null

        fun getDatabase(context: Context) : MyDatabase{
            return INSTANCE?: synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "Database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}