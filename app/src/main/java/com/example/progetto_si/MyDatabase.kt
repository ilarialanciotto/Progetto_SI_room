package com.example.progetto_si

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.progetto_si.Cliente.Cliente
import com.example.progetto_si.Cliente.ClienteDao
import com.example.progetto_si.Note.Note
import com.example.progetto_si.Note.NoteDao
import com.example.progetto_si.Registrazione.RegistrazioneDao
import com.example.progetto_si.Registrazione.Registrazioni

@Database(entities = [Registrazioni::class, Note::class, Cliente::class],version = 1, exportSchema = false)

abstract class MyDatabase: RoomDatabase() {

    abstract fun RegistrazioneDao() : RegistrazioneDao
    abstract fun NoteDao() : NoteDao
    abstract fun ClienteDao() : ClienteDao

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