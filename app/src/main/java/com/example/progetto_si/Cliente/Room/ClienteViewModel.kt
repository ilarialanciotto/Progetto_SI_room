package com.example.progetto_si.Cliente.Room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.progetto_si.ClassiUtili.Coppia
import com.example.progetto_si.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClienteViewModel(application: Application) : AndroidViewModel(application) {

    // Istanzia il DAO da Room
    private val clienteDao = MyDatabase.getDatabase(application).ClienteDao()


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

    fun getIdCliente(email : String,password: String, callback: (Int) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result =  clienteDao.getClienteByEmailAndPassword(email,password)
            withContext(Dispatchers.Main) {
                callback(result?.id ?: -1)
            }
        }
    }

    fun getAllClients(callback : (List<Cliente>)->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result =  clienteDao.getAllClients()
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    fun getClientiConPacchetti(callback : (List<Cliente>)->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result =  clienteDao.getClientiConPacchetti()
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }

    }

    // Recupera un cliente per email
    suspend fun getClienteByEmail(email: String): Cliente? = withContext(Dispatchers.IO) {
        clienteDao.getClienteByEmail(email)
    }

    // Aggiorna i dati del cliente
    suspend fun updateCliente(cliente: Cliente) = withContext(Dispatchers.IO) {
        clienteDao.updateCliente(cliente)
    }
}