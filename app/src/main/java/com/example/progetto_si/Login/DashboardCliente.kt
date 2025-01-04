package com.example.progetto_si.Login
import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.progetto_si.Cliente.Activity.AcquistaPacchettoActivity
import com.example.progetto_si.Cliente.Room.ClienteViewModel
import com.example.progetto_si.Cliente.Activity.PacchettiActivity
import com.example.progetto_si.Cliente.Activity.RichiesteActivity
import com.example.progetto_si.Note.Note
import com.example.progetto_si.Note.NoteViewModel
import com.example.progetto_si.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch



class DashboardCliente : AppCompatActivity() {

    private lateinit var calendar: CalendarView
    private lateinit var btnToggleCalendar: Button
    private lateinit var fab: FloatingActionButton

    private lateinit var clienteViewModel: ClienteViewModel
    private lateinit var noteViewModel: NoteViewModel

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_cliente)

        // Inizializza View
        calendar = findViewById(R.id.calendarView)
        btnToggleCalendar = findViewById(R.id.btnToggleCalendar)

        val username = intent.getStringExtra("EXTRA_USERNAME") ?: ""
        val textViewWelcome: TextView = findViewById(R.id.txView)

        // Inizializza ViewModel
        clienteViewModel = ViewModelProvider(this)[ClienteViewModel::class.java]
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        // Listener per Mostrare/Nascondere il Calendario
        btnToggleCalendar.setOnClickListener {
            toggleCalendarVisibility()
        }

        // Listener per le date selezionate
        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = formatDate(year, month, dayOfMonth)
            showNoteDialog(selectedDate, username)
        }

        // Gestione del FAB
        val fab: FloatingActionButton = findViewById(R.id.floatingActionButton2)
        fab.setOnClickListener {
            val popupMenu = PopupMenu(this, fab)
            popupMenu.menuInflater.inflate(R.menu.menu_fab, popupMenu.menu)
            popupMenu.setForceShowIcon(true)

            // Gestione del clic delle opzioni
            popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    //pacchetti acquistati dal cliente
                    R.id.action_pacchetti -> {
                        val intent = Intent(this@DashboardCliente, PacchettiActivity::class.java)
                        intent.putExtra("EXTRA_USERNAME", username)
                        startActivity(intent)
                        true
                    }
                    //note del cliente
                    R.id.action_richieste -> {
                        val intent = Intent(this@DashboardCliente, RichiesteActivity::class.java)
                        intent.putExtra("EXTRA_USERNAME", username)
                        startActivity(intent)
                        true
                    }
                    //shop pacchetti
                    R.id.action_acquista -> {
                        val intent = Intent(this@DashboardCliente, AcquistaPacchettoActivity::class.java)
                        intent.putExtra("EXTRA_USERNAME", username)
                        startActivity(intent)
                        true
                    }

                    else -> false
                }
            }

            // Mostra il popup
            popupMenu.show()
        }
    }

    private fun toggleCalendarVisibility() {
        calendar.visibility = if (calendar.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showNoteDialog(data: String, username: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_note, null)
        val dialogBuilder = AlertDialog.Builder(this).setView(dialogView).setCancelable(true)
        val layout = dialogView.findViewById<LinearLayout>(R.id.LL)
        val spinnerPacchetti = dialogView.findViewById<Spinner>(R.id.spinner_pacchetti)
        val dialog = dialogBuilder.create()

        // Dichiarazione globale di pacchettoIds
        var pacchettoIds: List<Int> = listOf()

        lifecycleScope.launch {
            // Carica i pacchetti acquistati
            clienteViewModel.getPacchettiCliente(username) { pacchetti ->
                val pacchettoNomi = pacchetti.map { it.nome }  // Nome dei pacchetti
                pacchettoIds = pacchetti.map { it.id }          // ID dei pacchetti

                // Crea un ArrayAdapter per lo Spinner
                val adapter = ArrayAdapter(
                    this@DashboardCliente,
                    android.R.layout.simple_spinner_dropdown_item,
                    pacchettoNomi
                )
                spinnerPacchetti.adapter = adapter // Imposta l'adapter dello Spinner

                // Gestione della selezione del pacchetto
                spinnerPacchetti.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parentView: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        // Memorizza l'ID del pacchetto selezionato
                        val pacchettoId = pacchettoIds[position]
                    }

                    override fun onNothingSelected(parentView: AdapterView<*>) {
                        // Comportamento se nessuna selezione è effettuata
                    }
                }
            }
        }

        dialog.show()

        val titleTextView = dialogView.findViewById<TextView>(R.id.txt)
        val noteEditText = dialogView.findViewById<EditText>(R.id.nota)
        val btn = dialogView.findViewById<Button>(R.id.btn)

        titleTextView.text = "Nota per il $data"

        btn.setOnClickListener {
            val notaT = noteEditText.text.toString()
            if (notaT.isNotEmpty()) {
                lifecycleScope.launch {
                    // Usa pacchettoIds aggiornato dal lifecycleScope
                    val pacchettoId = pacchettoIds[spinnerPacchetti.selectedItemPosition]

                    // Crea e salva la nota con l'ID del pacchetto selezionato
                    val nota = Note(
                        data = data,
                        email = username,
                        nota = notaT,
                        pacchetto = pacchettoId // Usa l'ID del pacchetto selezionato
                    )
                    noteViewModel.insert(nota)
                    Toast.makeText(
                        this@DashboardCliente,
                        "Nota salvata per il $data",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                }
            }
        }
    }


    private fun formatDate(year: Int, month: Int, day: Int): String {
        val monthFormatted = String.format("%02d", month + 1)
        val dayFormatted = String.format("%02d", day)
        return "$year-$monthFormatted-$dayFormatted"
    }
}
