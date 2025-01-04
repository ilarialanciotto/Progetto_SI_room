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
import com.example.progetto_si.Aquisti.Acquisti
import com.example.progetto_si.Aquisti.AcquistoDao
import com.example.progetto_si.Pacchetto.Pacchetto
import com.example.progetto_si.Pacchetto.PacchettoDao
import com.example.progetto_si.Sviluppatore.Sviluppatore
import com.example.progetto_si.Sviluppatore.SviluppatoreDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(
    entities = [Registrazioni::class, Note::class, Cliente::class, Admin::class, Sviluppatore::class, Pacchetto::class,
        Acquisti::class],
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
    abstract fun AcquistoDao() : AcquistoDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context) : MyDatabase{
            return INSTANCE?: synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "Database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
