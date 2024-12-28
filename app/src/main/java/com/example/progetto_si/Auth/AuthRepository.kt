package com.example.progetto_si.Auth

import com.example.progetto_si.Admin.AdminDao
import com.example.progetto_si.Cliente.ClienteDao
import com.example.progetto_si.Sviluppatore.SviluppatoreDao

class AuthRepository(
    private val adminDao: AdminDao,
    private val clienteDao: ClienteDao,
    private val sviluppatoreDao: SviluppatoreDao
) {
    suspend fun checkAdmin(username: String, password: String): Boolean {
        return adminDao.isAdmin(username, password)
    }

    suspend fun checkCliente(username: String, password: String): Boolean {
        return clienteDao.isCliente(username, password)
    }

    suspend fun checkSviluppatore(username: String, password: String): Boolean {
        return sviluppatoreDao.isSviluppatore(username, password)
    }
}
