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
                    R.id.action_pacchetti -> {
                        startActivity(Intent(this, PacchettiActivity::class.java))
                        true
                    }
                    R.id.action_richieste -> {
                        startActivity(Intent(this, RichiesteActivity::class.java))
                        true
                    }
                    R.id.action_acquista -> {
                        startActivity(Intent(this, AcquistaPacchettoActivity::class.java))
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
        val dialog = dialogBuilder.create()

        lifecycleScope.launch {
            noteViewModel.getNotesByDate(data, username) { notes ->
                if (notes.isNotEmpty()) {
                    layout.removeAllViews() // Pulire eventuali note precedenti
                    for (note in notes) {
                        val dynamicNoteEditText = EditText(this@DashboardCliente)
                        dynamicNoteEditText.setText(note)
                        dynamicNoteEditText.setBackgroundTintList(
                            ColorStateList.valueOf(Color.parseColor("#673AB7"))
                        )
                        dynamicNoteEditText.isFocusable = false
                        dynamicNoteEditText.isClickable = true
                        layout.addView(dynamicNoteEditText)

                        // Gestione del clic sulla nota
                        dynamicNoteEditText.setOnClickListener { view ->
                            noteViewModel.getNoteId(data, note, username) { id ->
                                if (id != -1) {
                                    dynamicNoteEditText.id = id
                                    val popupMenu = PopupMenu(this@DashboardCliente, view)
                                    popupMenu.menuInflater.inflate(R.menu.note_menu, popupMenu.menu)
                                    popupMenu.setForceShowIcon(true)
                                    popupMenu.setOnMenuItemClickListener { menuItem ->
                                        when (menuItem.itemId) {
                                            R.id.delete_note -> {
                                                noteViewModel.getNota(id) { nota ->
                                                    noteViewModel.deleteNota(nota)
                                                    layout.removeView(view)
                                                    Toast.makeText(
                                                        this@DashboardCliente,
                                                        "Nota eliminata",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                                true
                                            }

                                            else -> false
                                        }
                                    }
                                    popupMenu.show()
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

        titleTextView.text = "Nota per il $data"

        btn.setOnClickListener {
            val notaT = noteEditText.text.toString()
            if (notaT.isNotEmpty()) {
                lifecycleScope.launch {
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
