package com.example.progetto_si.Admin

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admins")
data class Admin(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String
)
