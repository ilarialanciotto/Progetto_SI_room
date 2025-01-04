package com.example.progetto_si.Note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.progetto_si.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel(application : Application) : AndroidViewModel(application) {

    private val NotaDao = MyDatabase.getDatabase(application).NoteDao()

    fun getNotesByUsername(username: String) = liveData {
        val result = NotaDao.getNotesByUsername(username)
        emit(result)
    }

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

    fun getNoteById(noteId: Int): LiveData<Note?> {
        return liveData {
            val note = NotaDao.getNoteById(noteId)
            emit(note)
        }
    }

    fun deleteNota(nota: Note){
        viewModelScope.launch (Dispatchers.IO){
            NotaDao.deleteNota(nota)
        }
    }

    fun getNotesByUser(email: String, callback: (List<Note>) -> Unit) {
        viewModelScope.launch {
            callback(NotaDao.getNotesByEmail(email))
        }
    }



}