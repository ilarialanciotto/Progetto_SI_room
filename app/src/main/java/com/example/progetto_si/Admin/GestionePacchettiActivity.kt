package com.example.progetto_si.Admin

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.ClassiUtili.PacchettoAdapter
import com.example.progetto_si.MyDatabase
import com.example.progetto_si.Pacchetto.Pacchetto
import com.example.progetto_si.Pacchetto.PacchettoViewModel
import com.example.progetto_si.R
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog

class GestionePacchettiActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PacchettoAdapter
    private val viewModel: PacchettoViewModel by viewModels()
    private var selectedPacchetto: Pacchetto? = null // Dichiarazione della variabile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestione_pacchetti)

        recyclerView = findViewById(R.id.recycler_pacchetti)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PacchettoAdapter(
            showBoolean = false,
            onClick = { pacchetto ->
                selectedPacchetto = pacchetto
                Toast.makeText(this,
                    getString(R.string.selezionato, pacchetto.nome), Toast.LENGTH_SHORT).show()
            }
        )
        recyclerView.adapter = adapter

        viewModel.pacchetti.observe(this) { pacchetti ->
            adapter.submitList(pacchetti)
        }

        // Aggiungi il listener per il click sugli elementi della RecyclerView
        recyclerView.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val child = rv.findChildViewUnder(e.x, e.y)
                if (child != null && e.action == MotionEvent.ACTION_UP) {
                    val position = rv.getChildAdapterPosition(child)
                    selectedPacchetto =
                        adapter.getItemAt(position) // Recupera il pacchetto selezionato
                    Toast.makeText(
                        this@GestionePacchettiActivity,
                        "Selezionato: ${selectedPacchetto?.nome}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return false
            }
        })
        // Bottone per aggiungere pacchetti
        findViewById<Button>(R.id.btn_aggiungi_pacchetto).setOnClickListener {
            mostraDialogAggiungiPacchetto()
        }

        // Bottone per eliminare pacchetti
        findViewById<Button>(R.id.btn_elimina_pacchetto).setOnClickListener {
            eliminaPacchetto() // Chiama la funzione eliminaPacchetto
        }
        val btnModificaPacchetto = findViewById<Button>(R.id.btn_modifica_pacchetto)
        btnModificaPacchetto.setOnClickListener {
            selectedPacchetto?.let { pacchetto ->
                showEditPacchettoDialog(pacchetto)
            } ?: Toast.makeText(this,
                getString(R.string.seleziona_un_pacchetto_da_modificare), Toast.LENGTH_SHORT)
                .show()
        }
    }
    private fun showEditPacchettoDialog(pacchetto: Pacchetto) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_modifica_pacchetto, null)
        val nomeEditText = dialogView.findViewById<EditText>(R.id.edit_nome_pacchetto)
        val prezzoEditText = dialogView.findViewById<EditText>(R.id.edit_prezzo_pacchetto)

        // Popola i campi con i dati attuali
        nomeEditText.setText(pacchetto.nome)
        prezzoEditText.setText(pacchetto.prezzo.toString())

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.modifica_pacchettO))
            .setView(dialogView)
            .setPositiveButton(getString(R.string.salVaa)) { _, _ ->
                val nuovoNome = nomeEditText.text.toString()
                val nuovoPrezzo = prezzoEditText.text.toString().toDoubleOrNull() ?: 0.0

                val pacchettoModificato = pacchetto.copy(nome = nuovoNome, prezzo = nuovoPrezzo)
                viewModel.Aggiorna(pacchettoModificato)
            }
            .setNegativeButton(getString(R.string.annullaa), null)
            .show()
    }

    private fun mostraDialogAggiungiPacchetto() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_aggiungi_pacchetto, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.aggiungi_pacchettO))
            .setView(dialogView)
            .setPositiveButton(R.string.aggiungi) { _, _ ->
                val nome = dialogView.findViewById<EditText>(R.id.editNomePacchetto).text.toString()
                val descrizione = dialogView.findViewById<EditText>(R.id.editDescrizionePacchetto).text.toString()
                val prezzo = dialogView.findViewById<EditText>(R.id.editPrezzoPacchetto).text.toString().toDoubleOrNull() ?: 0.0
                val durata = dialogView.findViewById<EditText>(R.id.editDurataPacchetto).text.toString()
                val hardware = dialogView.findViewById<EditText>(R.id.editHardwarePacchetto).text.toString()
                val software = dialogView.findViewById<EditText>(R.id.editSoftwarePacchetto).text.toString()

                val nuovoPacchetto = Pacchetto(
                    nome = nome,
                    descrizione = descrizione,
                    prezzo = prezzo,
                    durata = durata,
                    componenteHardware = hardware,
                    componenteSoftware = software
                )

                viewModel.insert(nuovoPacchetto)
                Toast.makeText(this, getString(R.string.pacchetto_aggiunto), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.AnNulla), null)
            .create()

        dialog.show()
    }

    // Funzione eliminaPacchetto
    private fun eliminaPacchetto() {
        selectedPacchetto?.let { pacchetto ->
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.conferma_eliminazione))
                .setMessage(getString(R.string.vuoi_eliminare_il_pacchetto, pacchetto.nome))
                .setPositiveButton(getString(R.string.elimina)) { _, _ ->
                    viewModel.eliminaPa(pacchetto)
                    Toast.makeText(this, getString(R.string.pacchetto_eliminato), Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(getString(R.string.ANnulla), null)
                .create()
                .show()
        } ?: Toast.makeText(this,
            getString(R.string.nessun_pacchetto_selezionato), Toast.LENGTH_SHORT).show()
    }

}