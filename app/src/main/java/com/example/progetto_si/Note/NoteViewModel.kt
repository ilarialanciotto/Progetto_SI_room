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

    private val notaDao = MyDatabase.getDatabase(application).NoteDao()

    fun getNotesByUsername(username: String): LiveData<List<Note>> = liveData(Dispatchers.IO) {
        val result = notaDao.getNotesByUsername(username)
        emit(result)
    }

    fun insert(nota: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notaDao.insertNote(nota)
        }
    }

    fun getNotesByDate(dt: String, user: String, callback: (List<String>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = notaDao.getNotesByDate(dt, user)
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    fun getNoteId(dt: String, nt: String, user: String, callback: (Int) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = notaDao.getNoteId(dt, nt, user)
            withContext(Dispatchers.Main) {
                callback(id ?: -1)
            }
        }
    }

    fun getNota(id: Int, callback: (Note) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val nota = notaDao.getNota(id)
            withContext(Dispatchers.Main) {
                callback(nota)
            }
        }
    }

    fun deleteNota(nota: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notaDao.deleteNota(nota)
        }
    }

    fun getNotesByUser(email: String, callback: (List<Note>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val notes = notaDao.getNotesByEmail(email)
            withContext(Dispatchers.Main) {
                callback(notes)
            }
        }
    }
}