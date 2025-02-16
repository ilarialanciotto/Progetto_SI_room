package com.example.progetto_si.Login

import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.Acquisti.AcquistiViewModel
import com.example.progetto_si.Admin.GestionePacchettiActivity
import com.example.progetto_si.ClassiUtili.AdapterStringNoedit
import com.example.progetto_si.ClassiUtili.PacchettoAdapter
import com.example.progetto_si.Cliente.Activity.*
import com.example.progetto_si.Cliente.Room.ClienteViewModel
import com.example.progetto_si.Note.Note
import com.example.progetto_si.Note.NoteViewModel
import com.example.progetto_si.Pacchetto.Pacchetto
import com.example.progetto_si.Pacchetto.PacchettoViewModel
import com.example.progetto_si.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import kotlin.getValue

class DashboardCliente : AppCompatActivity() {

    private lateinit var calendar: CalendarView
    private lateinit var btnToggleCalendar: Button
    private lateinit var fab: FloatingActionButton
    private lateinit var RW1: RecyclerView
    private lateinit var RW2: RecyclerView
    private lateinit var textView6: TextView
    private lateinit var textView7: TextView
    private lateinit var clienteViewModel: ClienteViewModel
    private lateinit var noteViewModel: NoteViewModel
    private val viewModel: PacchettoViewModel by viewModels()
    private var isCalendarVisible = false // Stato del calendario

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_cliente)

        // Inizializza le view
        calendar = findViewById(R.id.calendarView)
        btnToggleCalendar = findViewById(R.id.btnToggleCalendar)
        fab = findViewById(R.id.floatingActionButton2)
        textView6 = findViewById(R.id.textView6)
        textView7 = findViewById(R.id.textView7)
        RW1 = findViewById(R.id.r1)
        RW2 = findViewById(R.id.r2)

        // Imposta i layout dei RecyclerView
        RW1.layoutManager = LinearLayoutManager(this)
        RW2.layoutManager = LinearLayoutManager(this)

        // Inizializza ViewModel
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        val acquistoWM = AcquistiViewModel(application)
        clienteViewModel = ViewModelProvider(this)[ClienteViewModel::class.java]

        val username = intent.getStringExtra("EXTRA_USERNAME") ?: ""
        val password = intent.getStringExtra("EXTRA_PASSWORD") ?: ""

        // Imposta la visibilità iniziale
        calendar.visibility = View.GONE
        textView6.visibility = View.VISIBLE
        textView7.visibility = View.VISIBLE
        RW1.visibility = View.VISIBLE
        RW2.visibility = View.VISIBLE

        // Carica i pacchetti consigliati
        lifecycleScope.launch {
            clienteViewModel.getIdCliente(username, password) { id ->
                acquistoWM.getMaxPacchettoCliente(id) { pck ->
                    RW2.adapter = AdapterStringNoedit(pck)
                }
            }

            acquistoWM.getMaxPacchetto { pck ->
                RW1.adapter = AdapterStringNoedit(pck)
            }
        }

        // Listener per Mostrare/Nascondere il Calendario
        btnToggleCalendar.setOnClickListener {
            toggleCalendarVisibility()
        }

        // Listener per le date selezionate
        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = formatDate(year, month, dayOfMonth)
            showNoteDialog(selectedDate, username)
        }

        // Configura il Floating Action Button
        setupFab(username, password)
    }

    private fun toggleCalendarVisibility() {
        isCalendarVisible = !isCalendarVisible

        if (isCalendarVisible) {
            calendar.visibility = View.VISIBLE
            textView6.visibility = View.GONE
            textView7.visibility = View.GONE
            RW1.visibility = View.GONE
            RW2.visibility = View.GONE
        } else {
            calendar.visibility = View.GONE
            textView6.visibility = View.VISIBLE
            textView7.visibility = View.VISIBLE
            RW1.visibility = View.VISIBLE
            RW2.visibility = View.VISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setupFab(username: String, password: String) {
        fab.setOnClickListener {
            val popupMenu = PopupMenu(this, fab)
            popupMenu.menuInflater.inflate(R.menu.menu_fab, popupMenu.menu)
            popupMenu.setForceShowIcon(true)

            popupMenu.setOnMenuItemClickListener { item ->
                handleFabAction(item.itemId, username, password)
            }

            popupMenu.show()
        }
    }

    private fun handleFabAction(actionId: Int, username: String, password: String): Boolean {
        val intent = when (actionId) {
            R.id.action_pacchetti -> Intent(this, PacchettiActivity::class.java)
            R.id.action_richieste -> Intent(this, RichiesteActivity::class.java)
            R.id.action_acquista -> Intent(this, AcquistaPacchettoActivity::class.java)
            R.id.action_iMieiDati -> Intent(this, GestioneDatiActivity::class.java)
            else -> return false
        }

        intent.putExtra("EXTRA_USERNAME", username)
        intent.putExtra("EXTRA_PASSWORD", password)
        startActivity(intent)

        return true
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showNoteDialog(data: String, username: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_note, null)
        val layout = dialogView.findViewById<LinearLayout>(R.id.LL)
        val dialog = AlertDialog.Builder(this).setView(dialogView).setCancelable(true).create()

        val list_pk = dialogView.findViewById<Spinner>(R.id.spinnerPacchetto)
        var selectedPacchetto: Pacchetto? = null
        var id = -1  // Variabile per memorizzare l'ID del pacchetto

        viewModel.pacchetti.observe(this) { pacchetti ->
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                pacchetti.map { it.nome }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            list_pk.adapter = adapter

            // Se c'è almeno un pacchetto, seleziona il primo automaticamente
            if (pacchetti.isNotEmpty()) {
                selectedPacchetto = pacchetti[0]
                id = selectedPacchetto!!.id
            }
        }

        // Imposta il listener una sola volta, fuori dall'osservatore
        list_pk.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, itemId: Long) {
                selectedPacchetto = viewModel.pacchetti.value?.get(position)
                id = selectedPacchetto?.id ?: -1

                Toast.makeText(
                    this@DashboardCliente,
                    getString(R.string.selezionato, selectedPacchetto?.nome ?: ""),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedPacchetto = null
                id = -1
            }
        }

        lifecycleScope.launch {

            noteViewModel.getNotesByDate(data, username) { notes ->
                if (notes.isNotEmpty()) {
                    layout.removeAllViews()
                    notes.forEach { note ->
                        val noteEditText = EditText(this@DashboardCliente).apply {
                            setText(note)
                            backgroundTintList = ColorStateList.valueOf(Color.parseColor("#673AB7"))
                            isFocusable = false
                            isClickable = true
                        }
                        layout.addView(noteEditText)

                        noteEditText.setOnClickListener { view ->
                            noteViewModel.getNoteId(data, note, username) { id ->
                                if (id != -1) {
                                    showNotePopup(view, id, layout)
                                }
                            }
                        }
                    }
                }
            }
        }

        dialog.show()

        val titleTextView = dialogView.findViewById<TextView>(R.id.txt)
        val noteEditText = dialogView.findViewById<EditText>(R.id.nota)
        val btn = dialogView.findViewById<Button>(R.id.btn)

        titleTextView.text = getString(R.string.nota_per_il, data)

        btn.setOnClickListener {
            val notaT = noteEditText.text.toString()
            if (notaT.isNotEmpty()) {
                lifecycleScope.launch {
                    val nota = Note(
                        data = data,
                        email = username,
                        nota = notaT,
                        pacchetto = id
                    )
                    noteViewModel.insert(nota)
                    Toast.makeText(this@DashboardCliente,
                        getString(R.string.nota_salvata_per_il, data), Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showNotePopup(view: View, noteId: Int, layout: LinearLayout) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.note_menu, popupMenu.menu)
        popupMenu.setForceShowIcon(true)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.delete_note) {
                lifecycleScope.launch {
                    noteViewModel.getNota(noteId) { nota ->
                        noteViewModel.deleteNota(nota)
                        layout.removeView(view)
                        Toast.makeText(this@DashboardCliente,
                            getString(R.string.nota_eliminata), Toast.LENGTH_SHORT).show()
                    }
                }
                true
            } else false
        }
        popupMenu.show()
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        return "%04d-%02d-%02d".format(year, month + 1, day)
    }
}
