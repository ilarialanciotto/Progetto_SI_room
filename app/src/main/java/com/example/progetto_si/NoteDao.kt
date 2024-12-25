package com.example.progetto_si


import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun insertNote(nota : Note)

    @Query("SELECT nota FROM note WHERE data=:dt AND username=:user ")
    fun getNotesByDate(dt: String, user: String) : List<String>

    @Query("SELECT id FROM note WHERE username=:user AND data=:dt AND nota=:nt")
    fun getNoteId(dt: String, nt: String, user: String): Int?

    @Query("SELECT * FROM note WHERE id=:id")
    fun getNota(id:Int) : Note

    @Delete
    fun deleteNota(nota: Note)

//   @Update
//   fun AggiornaNota(nota : Note)

}