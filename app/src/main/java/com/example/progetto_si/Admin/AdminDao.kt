package com.example.progetto_si.Admin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.progetto_si.Cliente.Cliente

@Dao
interface AdminDao {

    @Insert
    fun insert(admin: Admin)

    @Query("SELECT * FROM admin WHERE id = :adminId")
    fun getAdminById(adminId: Int): Admin

    @Query("SELECT * FROM admin WHERE email = :email AND password = :password")
    fun getAdminByEmailAndPassword(email: String, password: String): Admin?

    @Query("SELECT COUNT(*) FROM admin WHERE email = :email AND password = :password")
    fun checkAdmin(email: String, password: String): Int

    @Query("SELECT * FROM admin")
    fun getAllAdmins(): List<Admin>
}
