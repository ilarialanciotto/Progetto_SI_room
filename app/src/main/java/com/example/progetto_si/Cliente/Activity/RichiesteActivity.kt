package com.example.progetto_si.Cliente.Activity

import android.widget.TextView
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.Cliente.Room.ClienteViewModel
import com.example.progetto_si.Note.NoteViewModel
import com.example.progetto_si.Note.Note
import com.example.progetto_si.R
import com.example.progetto_si.Note.NoteAdapter
import kotlinx.coroutines.launch

class RichiesteActivity : AppCompatActivity() {

        private lateinit var recyclerView: RecyclerView
        private lateinit var noteAdapter: NoteAdapter
        private lateinit var username: String
        private val noteViewModel: NoteViewModel by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_richieste)

            // Inizializza il RecyclerView
            recyclerView = findViewById(R.id.recycler_richieste)
            recyclerView.layoutManager = LinearLayoutManager(this)

            // Inizializza l'adapter con una lista vuota per l'aggiornamento successivo
            noteAdapter = NoteAdapter(emptyList())
            recyclerView.adapter = noteAdapter

            // Ottieni il nome utente dall'intent
            username = intent.getStringExtra("EXTRA_USERNAME") ?: ""

            // Carica le note
            loadNotes()
        }

        private fun loadNotes() {
            lifecycleScope.launch {
                noteViewModel.getNotesByUsername(username).observe(this@RichiesteActivity) { notes ->
                    if (notes != null && isActivityActive()) {
                        noteAdapter.updateNotes(notes)
                    }
                }
            }
        }

        /**
         * Funzione per verificare se l'Activity è attiva.
         */
        private fun isActivityActive(): Boolean {
            return !isFinishing && !isDestroyed
        }
    }
