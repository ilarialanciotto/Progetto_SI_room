package com.example.progetto_si.Cliente.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.progetto_si.Pacchetto.PacchettoAdapter
import com.example.progetto_si.R
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.Cliente.Room.ClienteViewModel

import kotlinx.coroutines.launch

class PacchettiActivity : AppCompatActivity() {

    private lateinit var clienteViewModel: ClienteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PacchettoAdapter
    private lateinit var textViewEmpty: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacchetti)

        val username = intent.getStringExtra("EXTRA_USERNAME") ?: ""

        // Initialize Views
        recyclerView = findViewById(R.id.recyclerViewPacchetti)
        textViewEmpty = findViewById(R.id.textViewEmpty)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the Adapter
        adapter = PacchettoAdapter { pacchetto ->
            // Handle package click if needed
        }
        recyclerView.adapter = adapter

        // Initialize ViewModel
        clienteViewModel = ViewModelProvider(this)[ClienteViewModel::class.java]

        // Fetch purchased packages
//        lifecycleScope.launch {
//            clienteViewModel.getPurchasedPacchetti(username) { pacchetti ->
//                if (pacchetti.isNotEmpty()) {
//                    textViewEmpty.visibility = View.GONE
//                    recyclerView.visibility = View.VISIBLE
//                    adapter.submitList(pacchetti)
//                } else {
//                    textViewEmpty.visibility = View.VISIBLE
//                    recyclerView.visibility = View.GONE
//                }
//            }
//        }
    }
}
