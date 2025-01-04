package com.example.progetto_si.Pacchetto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.R

class PacchettoAdapter(private val onClick: (Pacchetto) -> Unit) :
    RecyclerView.Adapter<PacchettoAdapter.PacchettoViewHolder>() {

    private var pacchettiList = listOf<Pacchetto>()

    class PacchettoViewHolder(view: View, private val onClick: (Pacchetto) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val nomeTextView: TextView = view.findViewById(R.id.txtNomePacchetto)
        private val prezzoTextView: TextView = view.findViewById(R.id.txtPrezzoPacchetto)
        private val btnAcquista: Button = view.findViewById(R.id.btnAcquista)

        private var pacchetto: Pacchetto? = null

        init {
            // Gestione del click per l'acquisto
            btnAcquista.setOnClickListener {
                pacchetto?.let {
                    onClick(it)  // Passa il pacchetto alla funzione onClick
                }
            }
        }

        // Funzione per associare i dati al view holder
        fun bind(pacchetto: Pacchetto) {
            this.pacchetto = pacchetto
            nomeTextView.text = pacchetto.nome
            prezzoTextView.text = "${pacchetto.prezzo} €"
        }
    }

    // Funzione per creare una nuova ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacchettoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pacchetto, parent, false)
        return PacchettoViewHolder(view, onClick)
    }

    // Funzione per associare i dati della lista all'holder
    override fun onBindViewHolder(holder: PacchettoViewHolder, position: Int) {
        holder.bind(pacchettiList[position])
    }

    // Restituisce il numero di elementi nella lista
    override fun getItemCount(): Int = pacchettiList.size

    // Funzione per aggiornare la lista dei pacchetti e notificare i cambiamenti
    fun submitList(list: List<Pacchetto>) {
        pacchettiList = list
        notifyDataSetChanged()  // Notifica il cambiamento nella lista
    }
}
