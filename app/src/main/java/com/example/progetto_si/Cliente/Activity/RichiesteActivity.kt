package com.example.progetto_si.Cliente.Activity

import android.widget.TextView
import android.os.Bundle
import android.view.View
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

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter
    private lateinit var textViewEmpty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_richieste)
    }
}
