package com.example.progetto_si.ClassiUtili

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.Pacchetto.Pacchetto
import com.example.progetto_si.R

class PacchettoAdapter(
    private val showBoolean: Boolean, // Rendi showBoolean una proprietà della classe
    private val onClick: (Pacchetto) -> Unit
) : RecyclerView.Adapter<PacchettoAdapter.PacchettoViewHolder>() {

    private var pacchettiList = listOf<Pacchetto>()

    class PacchettoViewHolder(
        view: View,
        private val showBoolean: Boolean, // Passa il parametro al ViewHolder
        private val onClick: (Pacchetto) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val nomeTextView: TextView = view.findViewById(R.id.txtNomePacchetto)
        private val prezzoTextView: TextView = view.findViewById(R.id.txtPrezzoPacchetto)
        private val btnAcquista: Button = view.findViewById(R.id.btnAcquista)
        private val descrionePacchetto: TextView = view.findViewById(R.id.txtDescrizionePacchetto)

        private var pacchetto: Pacchetto? = null

        init {
            // Gestione della visibilità del bottone
            btnAcquista.visibility = if (showBoolean) View.VISIBLE else View.GONE
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
            descrionePacchetto.text = pacchetto.descrizione
        }
    }

    // Funzione per creare una nuova ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacchettoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pacchetto, parent, false)
        return PacchettoViewHolder(view, showBoolean, onClick) // Passa showBoolean qui
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
