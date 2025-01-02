package com.example.progetto_si
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.progetto_si.Registrazione.Registrazioni
import com.example.progetto_si.Registrazione.RegistrazioneDao
import com.example.progetto_si.Cliente.Room.Cliente
import com.example.progetto_si.Cliente.Room.ClienteDao
import com.example.progetto_si.Note.Note
import com.example.progetto_si.Note.NoteDao
import com.example.progetto_si.Admin.Admin
import com.example.progetto_si.Admin.AdminDao
import com.example.progetto_si.Pacchetto.Pacchetto
import com.example.progetto_si.Pacchetto.PacchettoDao
import com.example.progetto_si.Sviluppatore.Sviluppatore
import com.example.progetto_si.Sviluppatore.SviluppatoreDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(
    entities = [Registrazioni::class, Note::class, Cliente::class, Admin::class, Sviluppatore::class, Pacchetto::class],
    version = 1,
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun RegistrazioneDao(): RegistrazioneDao
    abstract fun NoteDao(): NoteDao
    abstract fun ClienteDao(): ClienteDao
    abstract fun AdminDao(): AdminDao
    abstract fun SviluppatoreDao(): SviluppatoreDao
    abstract fun PacchettoDao(): PacchettoDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                )
                    .addCallback(DatabaseCallback(context)) // Callback per popolamento iniziale
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback(private val context: Context) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("DatabaseCallback", "Database creato. Inizio il popolamento...")
            CoroutineScope(Dispatchers.IO).launch {
                val database = getDatabase(context)
                populateDatabase(database)
            }
        }

        private fun populateDatabase(db: MyDatabase) {
            try {
                // Popolamento della tabella Cliente
                val clienteDao = db.ClienteDao()
                clienteDao.insert(
                    Cliente(
                        nome = "Mario",
                        cognome = "Rossi",
                        email = "mario.rossi@example.com",
                        password = "password123",
                        telefono = "1234567890",
                        azienda = "Example Corp",
                        tipo = "cliente"
                    )
                )
                clienteDao.insert(
                    Cliente(
                        nome = "Luca",
                        cognome = "Bianchi",
                        email = "luca.bianchi@example.com",
                        password = "securePass",
                        telefono = "0987654321",
                        azienda = "Tech Corp",
                        tipo = "cliente"
                    )
                )

                // Popolamento della tabella Admin
                val adminDao = db.AdminDao()
                adminDao.insert(
                    Admin(
                        nome = "Admin",
                        cognome = "SuperUser",
                        email = "admin@example.com",
                        password = "admin123",
                        ruolo = "admin"
                    )
                )

                // Popolamento della tabella Sviluppatore
                val sviluppatoreDao = db.SviluppatoreDao()
                sviluppatoreDao.insert(
                    Sviluppatore(
                        nome = "Marco",
                        cognome = "Verdi",
                        email = "marco.verdi@example.com",
                        password = "developer123",
                        livello = "senior",
                        progetti = "Progetto A, Progetto B",
                        tipo = "sviluppatore"
                    )
                )

                val pacchettoDao = db.PacchettoDao()
                pacchettoDao.insertPacchetto(
                    Pacchetto(
                        nome = "Pacchetto A",
                        descrizione = "Descrizione del pacchetto A",
                        prezzo = 99.99,
                        durata = "30 giorni"
                    )
                )
                pacchettoDao.insertPacchetto(
                    Pacchetto(
                        nome = "Pacchetto B",
                        descrizione = "Descrizione del pacchetto B",
                        prezzo = 149.99,
                        durata = "60 giorni"
                    )
                )
                pacchettoDao.insertPacchetto(
                    Pacchetto(
                        nome = "Pacchetto C",
                        descrizione = "Descrizione del pacchetto C",
                        prezzo = 199.99,
                        durata = "90 giorni"
                    )
                )

                Log.d("DatabaseCallback", "Popolamento completato con successo!")
            } catch (e: Exception) {
                Log.e("DatabaseCallback", "Errore durante il popolamento: ${e.message}")
            }
        }
    }
}
