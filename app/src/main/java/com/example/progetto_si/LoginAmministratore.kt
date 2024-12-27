package com.example.progetto_si

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.progetto_si.Note.Note
import com.example.progetto_si.Note.NoteViewModel
import kotlinx.coroutines.launch

class LoginAmministratore : AppCompatActivity() {

    private lateinit var calendar : CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_amministratore)

        calendar = findViewById(R.id.calendarView)
        val username = intent.getStringExtra("EXTRA_USERNAME")
        val textViewWelcome: TextView = findViewById(R.id.txView)
        textViewWelcome.setText("Ciao $username!")

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = formatDate(year, month, dayOfMonth)
            showNoteDialog(selectedDate,username.toString())
        }
    }

    private fun showNoteDialog(data: String, username : String) {

        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_note, null)
        val dialogBuilder = AlertDialog.Builder(this).setView(dialogView).setCancelable(true)
        val Layout = dialogView.findViewById<LinearLayout>(R.id.LL)
        val dialog = dialogBuilder.create()
        var NoteViewModel = NoteViewModel(application)


        lifecycleScope.launch{
            NoteViewModel.getNotesByDate(data,username) { notes->
                if (notes.isNotEmpty()){
                    for (note in notes) {
                        var dynamicNoteEditText  = EditText(this@LoginAmministratore)
                        dynamicNoteEditText.setText(note)
                        dynamicNoteEditText.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#673AB7")))
                        dynamicNoteEditText.isFocusableInTouchMode = false
                        dynamicNoteEditText.isFocusable = false
                        dynamicNoteEditText.setKeyListener(null)
                        Layout.addView(dynamicNoteEditText)
                        dynamicNoteEditText.setOnClickListener { view ->
                            NoteViewModel.getNoteId(data,dynamicNoteEditText.text.toString(),username) { id->
                                if (id!=-1){
                                    dynamicNoteEditText.id=id
                                    val popupMenu = PopupMenu(this@LoginAmministratore, view)
                                    val inflater = popupMenu.menuInflater
                                    inflater.inflate(R.menu.note_menu, popupMenu.menu)
                                    popupMenu.setForceShowIcon(true)
                                    popupMenu.setOnMenuItemClickListener { menuItem ->
                                        when (menuItem.itemId) {
                                            R.id.delete_note -> {
                                                NoteViewModel.getNota(dynamicNoteEditText.id) { nota ->
                                                    NoteViewModel.deleteNota(nota)
                                                    (view.parent as? LinearLayout)?.removeView(view)
                                                    Toast.makeText(this@LoginAmministratore, "Nota eliminata" , Toast.LENGTH_SHORT).show()
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
                    val nota = Note(
                        data = data,
                        username= username,
                        nota = notaT)
                    NoteViewModel.insert(nota)
                    Toast.makeText(this@LoginAmministratore, "Nota salvata per il $data", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.dismiss()
        }
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val monthFormatted = String.format("%02d", month + 1)
        val dayFormatted = String.format("%02d", day)
        return "$year-$monthFormatted-$dayFormatted"
    }

}