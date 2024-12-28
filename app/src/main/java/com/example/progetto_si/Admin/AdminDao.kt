package com.example.progetto_si.Admin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AdminDao {
    @Query("SELECT COUNT(*) > 0 FROM admins WHERE username = :username AND password = :password")
    fun isAdmin(username: String, password: String): Boolean

    @Query("SELECT * FROM admins WHERE username = :username")
    fun getAdminByUsername(username: String): Admin?

    @Insert
    fun insertAll(vararg admins: Admin)
}
