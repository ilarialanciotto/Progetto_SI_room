package com.example.progetto_si.Admin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.progetto_si.MyDatabase
import kotlinx.coroutines.launch

class AdminViewModel(application: Application) : AndroidViewModel(application) {

    private val adminDao: AdminDao

    init {
        val db = MyDatabase.getDatabase(application)
        adminDao = db.AdminDao()
    }

    fun checkCredenziali(username: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isValid = adminDao.isAdmin(username, password)
            callback(isValid)
        }
    }

    fun getAdmin(username: String, callback: (Admin?) -> Unit) {
        viewModelScope.launch {
            val admin = adminDao.getAdminByUsername(username)
            callback(admin)
        }
    }
}
