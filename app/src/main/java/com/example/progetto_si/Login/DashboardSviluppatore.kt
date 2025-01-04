package com.example.progetto_si.Login

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.Aquisti.AcquistiViewModel
import com.example.progetto_si.ClassiUtili.AdapterStringNoedit
import com.example.progetto_si.ClassiUtili.StringAdapter
import com.example.progetto_si.Cliente.Room.ClienteViewModel
import com.example.progetto_si.Pacchetto.Pacchetto
import com.example.progetto_si.Pacchetto.PacchettoViewModel
import com.example.progetto_si.R
import kotlinx.coroutines.launch

class DashboardSviluppatore : AppCompatActivity() {

    var show = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_sviluppatore)

        val username = intent.getStringExtra("EXTRA_USERNAME")
        val textViewWelcome: TextView = findViewById(R.id.txView)
        textViewWelcome.setText("Ciao $username!")

        val recyclerView: RecyclerView = findViewById(R.id.lista)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val num: RecyclerView = findViewById(R.id.Num)
        num.layoutManager = LinearLayoutManager(this)
        var packVM= PacchettoViewModel(application)
        var acquistoWM = AcquistiViewModel(application)
        var clWM = ClienteViewModel(application)

        lifecycleScope.launch {
            packVM.getAllid { ListId ->
                val tempListN = MutableList(ListId.size) { "" }
                for (id in ListId) {
                    acquistoWM.getNumeroAcquisti(id) { n ->
                        tempListN[id - 1] = n.toString()
                        if (tempListN.size == ListId.size) {
                            val adapter2 = AdapterStringNoedit(tempListN.toList())
                            num.adapter = adapter2
                        }
                    }
                }
            }

            packVM.getAllPacchetti { dati->
                val adapter = StringAdapter(dati) { item ->
                    if (!show){
                        showItemDialog(item,packVM,clWM)
                        show = true
                    }
                }
                recyclerView.adapter = adapter
            }
        }
    }

    private fun showItemDialog(item : String, p : PacchettoViewModel,clWM : ClienteViewModel) {

        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_custom_options, null)
        val dialogBuilder = AlertDialog.Builder(this).setView(dialogView).setCancelable(true)
        val dialog = dialogBuilder.create()
        var layout = dialogView.findViewById<LinearLayout>(R.id.LayoutL)
        dialogView.findViewById<TextView>(R.id.txTT).text= "$item"
        var EditT = ArrayList<EditText>()
        var oldDettagli = ArrayList<String>()
        var idP =-1
        var count=0

        p.getDettaggliPacchetto(item) { dettagli->
            for (info in dettagli){
                if (count==0) idP=info.toInt()
                count++
                oldDettagli.add(info)
                var dynamicEditText  = EditText(this)
                dynamicEditText.setText(info)
                dynamicEditText.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#673AB7")))
                dynamicEditText.isFocusableInTouchMode = true
                dynamicEditText.isFocusable = true
                layout.addView(dynamicEditText)
                EditT.add(dynamicEditText)
            }

            val ListN: RecyclerView = dialogView.findViewById<RecyclerView>(R.id.ListaNote)
            ListN.layoutManager = LinearLayoutManager(this)
            val ListC: RecyclerView = dialogView.findViewById<RecyclerView>(R.id.listaClienti)
            ListC.layoutManager = LinearLayoutManager(this)
            var  listaR = ArrayList<String>()
            var listaC = ArrayList<String>()
            clWM.getNotaClientePacchetto(idP) {  note->
                for (riga in note){
                    listaR.add(riga.nota)
                    listaC.add(riga.email)
                    val adapter = AdapterStringNoedit(listaR)
                    ListN.adapter = adapter
                    val adapter2 = AdapterStringNoedit(listaC)
                    ListC.adapter = adapter2
                }
            }
            dialog.show()
        }


        dialogView.findViewById<Button>(R.id.modifica).setOnClickListener {
            val updatedPacchetto = Pacchetto(
                id = oldDettagli[0].toInt(),
                nome = oldDettagli[1],
                prezzo = oldDettagli[2].toDouble(),
                descrizione = oldDettagli[3],
                durata = EditT[4].text.toString(),
                componenteHardware = oldDettagli[5],
                componenteSoftware = oldDettagli[6]
            )
            p.Aggiorna(updatedPacchetto)
        }

        dialogView.findViewById<Button>(R.id.esci).setOnClickListener {
            show = false
            dialog.dismiss()
        }
    }

}