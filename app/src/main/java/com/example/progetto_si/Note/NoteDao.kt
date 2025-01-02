package com.example.progetto_si.Note


import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun insertNote(nota : Note)

    @Query("SELECT * FROM note WHERE email=:email")
    fun getNotesByUsername(email: String): List<Note>

    @Query("SELECT nota FROM note WHERE data=:dt AND email=:user ")
    fun getNotesByDate(dt: String, user: String) : List<String>

    @Query("SELECT id FROM note WHERE email=:user AND data=:dt AND nota=:nt")
    fun getNoteId(dt: String, nt: String, user: String): Int?

    @Query("SELECT * FROM note WHERE id=:id")
    fun getNota(id:Int) : Note

    @Query("SELECT * FROM note")
    fun getNotes() : MutableList<Note>

    @Delete
    fun deleteNota(nota: Note)

    @Query("SELECT * FROM note WHERE id = :noteId")
    fun getNoteById(noteId: Int): Note?

    @Query("SELECT * FROM note WHERE email = :email")
    fun getNotesByEmail(email: String): List<Note>


}