package com.example.progetto_si.Pacchetto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.R

class PacchettoAdapter(
    private val onPacchettoClick: (Pacchetto) -> Unit
) : RecyclerView.Adapter<PacchettoAdapter.PacchettoViewHolder>() {

    private var pacchetti = emptyList<Pacchetto>()

    fun submitList(list: List<Pacchetto>) {
        pacchetti = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacchettoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pacchetto, parent, false)
        return PacchettoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PacchettoViewHolder, position: Int) {
        val pacchetto = pacchetti[position]
        holder.bind(pacchetto)
        holder.itemView.setOnClickListener {
            onPacchettoClick(pacchetto)
        }
    }

    override fun getItemCount() = pacchetti.size

    class PacchettoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomePacchetto: TextView = itemView.findViewById(R.id.nomePacchetto)
        private val prezzoPacchetto: TextView = itemView.findViewById(R.id.prezzoPacchetto)

        fun bind(pacchetto: Pacchetto) {
            nomePacchetto.text = pacchetto.nome
            prezzoPacchetto.text = "${pacchetto.prezzo} €"
        }
    }
}
