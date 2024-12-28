package com.example.progetto_si

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.progetto_si.Admin.Admin
import com.example.progetto_si.Admin.AdminDao
import com.example.progetto_si.Cliente.Cliente
import com.example.progetto_si.Cliente.ClienteDao
import com.example.progetto_si.Note.Note
import com.example.progetto_si.Note.NoteDao
import com.example.progetto_si.Pacchetto.Pacchetto
import com.example.progetto_si.Pacchetto.PacchettoDao
import com.example.progetto_si.Registrazione.RegistrazioneDao
import com.example.progetto_si.Registrazione.Registrazioni
import com.example.progetto_si.Sviluppatore.Sviluppatore
import com.example.progetto_si.Sviluppatore.SviluppatoreDao
import java.util.concurrent.Executors

@Database(entities = [Registrazioni::class, Note::class, Cliente::class, Sviluppatore::class, Admin::class],version = 1, exportSchema = false)

abstract class MyDatabase: RoomDatabase() {

    abstract fun RegistrazioneDao() : RegistrazioneDao
    abstract fun NoteDao() : NoteDao
    abstract fun ClienteDao() : ClienteDao
    abstract fun SviluppatoreDao() : SviluppatoreDao
    abstract fun AdminDao() : AdminDao

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

    private class DatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Inserimento statico dei dati al momento della creazione del database
            Executors.newSingleThreadExecutor().execute {
                val database = INSTANCE ?: return@execute

                // Inserisci Admin
                database.AdminDao().insertAll(
                    Admin(1,"admin1", "password1"),
                    Admin(2,"admin2", "password2"),
                    Admin(3,"admin3", "password3")
                )

                // Inserisci Sviluppatori
                database.SviluppatoreDao().insertAll(
                    Sviluppatore(username = "dev1", password = "password1"),
                    Sviluppatore(username = "dev2", password = "password2"),
                    Sviluppatore(username = "dev3", password = "password3")
                )

                // Inserisci Clienti
                database.ClienteDao().insertAll(
                    Cliente(
                        1, "mario", "mario@mail.com", "password1", "123 234 4567", "null"),
                    Cliente(2, "luigi", "luigi@mail.com", "password2", "234 345 5678", "null"),
                    Cliente(3, "francesco", "francesco@mail.com", "password3", "345 456 6789", "null")
                    )
            }
        }
    }
}

