package com.example.progetto_si.Cliente.Room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.progetto_si.ClassiUtili.Coppia
import com.example.progetto_si.MyDatabase
import com.example.progetto_si.Pacchetto.Pacchetto
import com.example.progetto_si.Pacchetto.PacchettoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClienteViewModel(application: Application) : AndroidViewModel(application) {

    // Istanzia il DAO da Room
    private val clienteDao = MyDatabase.getDatabase(application).ClienteDao()
    private val pacchettoDao: PacchettoDao =
        Room.databaseBuilder(application, MyDatabase::class.java, "Database")
            .build()
            .PacchettoDao()


    fun insert(cliente: Cliente) {
        viewModelScope.launch(Dispatchers.IO) {
            clienteDao.insert(cliente)
        }
    }

    fun getNotaClientePacchetto(idPacchetto : Int, callback : (List<Coppia>)->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = clienteDao.getNotaClientePacchetto(idPacchetto)
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    fun insertCliente(nome: String, cognome: String, email: String, password: String, telefono: String, azienda: String, tipo: String) {
        val cliente = Cliente(
            nome = nome,
            cognome = cognome,
            email = email,
            password = password,
            telefono = telefono,
            azienda = azienda,
            tipo = tipo
        )
        viewModelScope.launch(Dispatchers.IO) {
            clienteDao.insert(cliente)
        }
    }




//    fun getPurchasedPacchetti(email: String, callback: (List<Pacchetto>) -> Unit) {
//        viewModelScope.launch {
//            callback(ClienteDao.getPurchasedPacchetti(email))
//        }
//    }


}
