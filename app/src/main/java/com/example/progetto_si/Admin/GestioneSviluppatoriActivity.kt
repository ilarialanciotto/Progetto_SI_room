package com.example.progetto_si.Admin

import android.widget.EditText
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.R
import com.example.progetto_si.Sviluppatore.Sviluppatore
import com.example.progetto_si.Sviluppatore.SviluppatoreAdapter
import com.example.progetto_si.Sviluppatore.SviluppatoreViewModel

class GestioneSviluppatoriActivity : AppCompatActivity() {

    private lateinit var viewModel: SviluppatoreViewModel
    private var selectedSviluppatore: Sviluppatore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestione_sviluppatori)

        viewModel = ViewModelProvider(this).get(SviluppatoreViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_sviluppatori)
        val adapter = SviluppatoreAdapter { sviluppatore ->
            selectedSviluppatore = sviluppatore
            Toast.makeText(
                this,
                getString(R.string.Selezionato, sviluppatore.nome),
                Toast.LENGTH_SHORT
            ).show()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        Thread {
            val sviluppatori = viewModel.getAllSviluppatori()
            runOnUiThread {
                adapter.submitList(sviluppatori)
            }
        }.start()

        // Bottone per aggiungere un nuovo sviluppatore
        findViewById<Button>(R.id.btn_aggiungi_sviluppatore).setOnClickListener {
            showAddSviluppatoreDialog()
        }

        // Bottone per modificare lo sviluppatore selezionato
        findViewById<Button>(R.id.btn_modifica_sviluppatore).setOnClickListener {
            selectedSviluppatore?.let {
                showEditSviluppatoreDialog(it)
            } ?: Toast.makeText(this,
                getString(R.string.seleziona_uno_sviluppatore_da_modificare), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAddSviluppatoreDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_aggiungi_sviluppatore, null)
        val nomeEditText = dialogView.findViewById<EditText>(R.id.edit_nome_sviluppatore)
        val cognomeEditText = dialogView.findViewById<EditText>(R.id.edit_cognome_sviluppatore)
        val emailEditText = dialogView.findViewById<EditText>(R.id.edit_email_sviluppatore)
        val ruoloEditText = dialogView.findViewById<EditText>(R.id.edit_ruolo_sviluppatore)
        val livelloEditText = dialogView.findViewById<EditText>(R.id.edit_livello_sviluppatore)
        val progettiEditText = dialogView.findViewById<EditText>(R.id.edit_progetti_sviluppatore)

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.aggiungi_sviluppatorE))
            .setView(dialogView)
            .setPositiveButton(R.string.aggiungi) { _, _ ->
                val nome = nomeEditText.text.toString()
                val cognome = cognomeEditText.text.toString()
                val email = emailEditText.text.toString()
                val livello = livelloEditText.text.toString()
                val progetti = progettiEditText.text.toString()

                if (nome.isNotEmpty() && cognome.isNotEmpty() && email.isNotEmpty()) {
                    val nuovoSviluppatore = Sviluppatore(
                        nome = nome,
                        cognome = cognome,
                        email = email,
                        password = "default",
                        livello = livello,
                        progetti = progetti,
                        tipo = "sviluppatore"
                    )
                    viewModel.insert(nuovoSviluppatore)
                    Toast.makeText(this,
                        getString(R.string.sviluppatore_aggiunto), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,
                        getString(R.string.compila_tutti_i_campi), Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(R.string.annullaa, null)
            .show()
    }
    private fun showEditSviluppatoreDialog(sviluppatore: Sviluppatore) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_modifica_sviluppatore, null)
        val nomeEditText = dialogView.findViewById<EditText>(R.id.edit_nome_sviluppatore)
        val cognomeEditText = dialogView.findViewById<EditText>(R.id.edit_cognome_sviluppatore)
        val emailEditText = dialogView.findViewById<EditText>(R.id.edit_email_sviluppatore)
        val ruoloEditText = dialogView.findViewById<EditText>(R.id.edit_ruolo_sviluppatore)
        val livelloEditText = dialogView.findViewById<EditText>(R.id.edit_livello_sviluppatore)
        val progettiEditText = dialogView.findViewById<EditText>(R.id.edit_progetti_sviluppatore)

        nomeEditText.setText(sviluppatore.nome)
        cognomeEditText.setText(sviluppatore.cognome)
        emailEditText.setText(sviluppatore.email)
        livelloEditText.setText(sviluppatore.livello)
        progettiEditText.setText(sviluppatore.progetti)

        AlertDialog.Builder(this)
            .setTitle(R.string.modifica_sviluppatore)
            .setView(dialogView)
            .setPositiveButton(R.string.salva) { _, _ ->
                val nome = nomeEditText.text.toString()
                val cognome = cognomeEditText.text.toString()
                val email = emailEditText.text.toString()
                val livello = livelloEditText.text.toString()
                val progetti = progettiEditText.text.toString()

                if (nome.isNotEmpty() && cognome.isNotEmpty() && email.isNotEmpty()) {
                    val sviluppatoreModificato = sviluppatore.copy(
                        nome = nome,
                        cognome = cognome,
                        email = email,
                        livello = livello,
                        progetti = progetti
                    )
                    viewModel.update(sviluppatoreModificato)
                    Toast.makeText(this,
                        getString(R.string.sviluppatore_modificato), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,
                        getString(R.string.compila_tutti_i_campI), Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(R.string.annullaa, null)
            .show()
    }
}