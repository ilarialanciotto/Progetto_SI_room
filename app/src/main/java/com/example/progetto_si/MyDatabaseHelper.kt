package com.example.progetto_si

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class MyDatabaseHelper(context: Context): SQLiteOpenHelper(context, "Mydatabase.db",
    null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE registrazioni(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT, " +
                "cognome TEXT," +
                "username TEXT , " +
                "eta INTEGER  ," +
                "password TEXT);")

        db.execSQL("CREATE TABLE Note(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "data TEXT , " +
                "username TEXT , " +
                "nota TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS registrazioni")
        db.execSQL("DROP TABLE IF EXISTS note")
        onCreate(db)
    }

    fun insertRegistrazioni(nome: String, surname : String, username: String, eta: Int, password : String) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply{
            put("nome", nome)
            put("cognome", surname)
            put("username", username)
            put("eta", eta)
            put("password", password)
        }
        val result = db.insert("registrazioni", null, values)
        return result != -1L
    }

    fun insertNota(data: String, surname : String, nota : String) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply{
            put("data", data)
            put("username", surname)
            put("nota", nota)
        }
        val result = db.insert("note", null, values)
        return result != -1L
    }

    fun getAllNames(): List<String> {
        val list = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT username FROM registrazioni", null)
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(0)
                list.add(name)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    fun checkReg(username: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT COUNT(*) FROM registrazioni WHERE username = ?"
        val cursor = db.rawQuery(query, arrayOf(username))
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count > 0
    }

    fun checkCredenziali(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT COUNT(*) FROM registrazioni WHERE username = ? AND password = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count > 0
    }

    fun getNotesByDate(date: String, username: String): List<String> {
        val notes = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.query(
            "Note",
            arrayOf("nota"),
            "data = ? AND username = ?",
            arrayOf(date, username),
            null, null, null
        )

        if (cursor.moveToFirst()) {
            do {
                val note = cursor.getString(cursor.getColumnIndex("nota"))
                notes.add(note)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return notes
    }

    fun getNoteId(data: String, nota: String, username: String): Int? {
        val db = this.readableDatabase
        val cursor = db.query(
            "Note",  // Nome della tabella
            arrayOf("_id"),  // Colonna che vogliamo ottenere (ID della nota)
            "data = ? AND nota = ? AND username = ?",  // Condizioni
            arrayOf(data, nota, username),  // Valori delle condizioni
            null,  // Gruppo
            null,  // Avere
            null   // Ordine
        )

        var noteId: Int? = null
        if (cursor.moveToFirst()) {
            noteId = cursor.getInt(cursor.getColumnIndex("_id"))
        }
        cursor.close()
        return noteId
    }

    fun deleteNoteById(noteId: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete("Note", "_id = ?", arrayOf(noteId.toString()))
        return result > 0
    }

    fun searchNames(query: String): List<String> {
        val list = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT username FROM registrazioni WHERE username LIKE ?", arrayOf("%$query%"))
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(0)
                list.add(name)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

}
