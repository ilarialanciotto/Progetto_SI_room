package com.example.progetto_si.Sviluppatore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.R

class SviluppatoreAdapter(
    private val onItemClick: (Sviluppatore) -> Unit
) : RecyclerView.Adapter<SviluppatoreAdapter.SviluppatoreViewHolder>() {

    private var sviluppatoriList = listOf<Sviluppatore>()

    class SviluppatoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeTextView: TextView = itemView.findViewById(R.id.txtNomeSviluppatore)

        fun bind(sviluppatore: Sviluppatore, onItemClick: (Sviluppatore) -> Unit) {
            nomeTextView.text = sviluppatore.nome
            itemView.setOnClickListener {
                onItemClick(sviluppatore)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SviluppatoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sviluppatore, parent, false)
        return SviluppatoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: SviluppatoreViewHolder, position: Int) {
        holder.bind(sviluppatoriList[position], onItemClick)
    }

    override fun getItemCount(): Int = sviluppatoriList.size

    fun submitList(list: List<Sviluppatore>) {
        sviluppatoriList = list
        notifyDataSetChanged()
    }
}