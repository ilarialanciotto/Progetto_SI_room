package com.example.progetto_si.Acquisti

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.progetto_si.MyDatabase
import com.example.progetto_si.Pacchetto.Pacchetto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AcquistiViewModel (application : Application) : AndroidViewModel(application) {

    private val acquistoDao = MyDatabase.getDatabase(application).AcquistoDao()

    fun insert(acquisto : Acquisti){
        viewModelScope.launch(Dispatchers.IO) {
            acquistoDao.insertAcquisto(acquisto)
        }
    }

    fun getNumeroAcquistiCliente(cl : Int , callback : (Int)->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = acquistoDao.getNumCliePacch(cl)
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    fun getNumeroAcquisti(pk : Int , callback : (Int)->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = acquistoDao.getNumCliePacch(pk)
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    fun getPacchettoById(idCliente: Int, callback: (List<Pacchetto>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = acquistoDao.getPacchettoById(idCliente)
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

}
