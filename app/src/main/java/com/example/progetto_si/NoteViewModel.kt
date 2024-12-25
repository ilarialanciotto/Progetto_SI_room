package com.example.progetto_si

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel(application : Application) : AndroidViewModel(application) {

    private val NotaDao = MyDatabase.getDatabase(application).NoteDao()

    fun insert(nota: Note) {
        viewModelScope.launch (Dispatchers.IO){
            NotaDao.insertNote(nota)
        }
    }

    fun getNotesByDate(dt: String, user: String , callback: (List<String>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = NotaDao.getNotesByDate(dt,user)
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    fun getNoteId(dt: String, nt: String, user: String ,callback: (Int) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = NotaDao.getNoteId(dt,nt,user)
            withContext(Dispatchers.Main) {
                if (id!=null)  callback(id)
                callback(-1)
            }
        }
    }

    fun getNota(id : Int, callback: (Note)->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val nota = NotaDao.getNota(id)
            withContext(Dispatchers.Main) {
                callback(nota)
            }
        }
    }

    fun deleteNota(nota: Note){
        viewModelScope.launch (Dispatchers.IO){
            NotaDao.deleteNota(nota)
        }
    }

}