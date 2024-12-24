package com.example.progetto_si

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class LogActivity : AppCompatActivity() {

    lateinit var calendar : CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        val dbHelper= MyDatabaseHelper(this)
        val db=dbHelper.readableDatabase
        calendar = findViewById(R.id.calendarView)
        val username = intent.getStringExtra("EXTRA_USERNAME")
        val textViewWelcome: TextView = findViewById(R.id.txView)
        val search : SearchView = findViewById(R.id.CercaVW)
        val lista : ListView = findViewById(R.id.ListVW)
        val items = dbHelper.getAllNames()
        var adapter: ArrayAdapter<String>

        textViewWelcome.setText("Ciao $username!")

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lista.adapter = adapter

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = formatDate(year, month, dayOfMonth)
            showNoteDialog(selectedDate,username.toString(),dbHelper)
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText.orEmpty()
                if (searchText.isNotEmpty()) {
                    val results = dbHelper.searchNames(searchText)
                    adapter.clear()
                    adapter.addAll(results)
                    lista.visibility = if (results.isEmpty()) View.GONE else View.VISIBLE
                } else {
                    lista.visibility = View.GONE
                }
                adapter.notifyDataSetChanged()
                return true
            }
        })
    }

    private fun showNoteDialog(data: String,username : String, db : MyDatabaseHelper) {

        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_note, null)
        val dialogBuilder = AlertDialog.Builder(this).setView(dialogView).setCancelable(true)
        val Layout = dialogView.findViewById<LinearLayout>(R.id.LL)
        val dialog = dialogBuilder.create()


        val notes = db.getNotesByDate(data,username)
        if (notes.isNotEmpty()){
            for (note in notes) {
                val dynamicNoteEditText = TextView(this)
                dynamicNoteEditText.setText(note)
                Layout.addView(dynamicNoteEditText)
                dynamicNoteEditText.setOnClickListener { view ->
                    var id=db.getNoteId(data,dynamicNoteEditText.text.toString(),username)
                    if(id!=null) dynamicNoteEditText.id=id
                    val popupMenu = PopupMenu(this, view)
                    val inflater = popupMenu.menuInflater
                    inflater.inflate(R.menu.note_menu, popupMenu.menu)  // Inflaziona un file di menu XML
                    popupMenu.setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.delete_note -> {
                                if(dynamicNoteEditText.id!=-1) db.deleteNoteById(dynamicNoteEditText.id)
                                (view.parent as? LinearLayout)?.removeView(view)
                                Toast.makeText(this, "Nota eliminata" , Toast.LENGTH_SHORT).show()
                                true
                            }
                            else -> false
                        }
                    }
                    popupMenu.show()
                }
            }
        }

        dialog.show()

        val titleTextView = dialogView.findViewById<TextView>(R.id.txt)
        val noteEditText = dialogView.findViewById<EditText>(R.id.nota)
        val btn = dialogView.findViewById<Button>(R.id.btn)

        titleTextView.text = "Nota per il $data"

        btn.setOnClickListener {
            val note = noteEditText.text.toString()
            if (note.isNotEmpty()) {
                db.insertNota(data, username, note)
                Toast.makeText(this, "Nota salvata per il $data", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val monthFormatted = String.format("%02d", month + 1) // Mesi da 0 a 11, quindi +1
        val dayFormatted = String.format("%02d", day)
        return "$year-$monthFormatted-$dayFormatted"
    }

}