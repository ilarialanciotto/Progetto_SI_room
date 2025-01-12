package com.example.progetto_si.ClassiUtili

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.Cliente.Room.ClienteConPacchetti
import com.example.progetto_si.R

class ClienteAdapter : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

    private var clienti: List<ClienteConPacchetti> = listOf()

    class ClienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeTextView: TextView = itemView.findViewById(R.id.txt_nome)
        private val emailTextView: TextView = itemView.findViewById(R.id.txt_email)
        private val pacchettiTextView: TextView = itemView.findViewById(R.id.txt_numero_pacchetti)

        fun bind(cliente: ClienteConPacchetti) {
            nomeTextView.text = cliente.nome
            emailTextView.text = cliente.email
            pacchettiTextView.text = "Pacchetti: ${cliente.numeroPacchetti}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = clienti[position] // Accedi direttamente alla lista
        holder.bind(cliente)
    }
    object DiffCallback : DiffUtil.ItemCallback<ClienteConPacchetti>() {
        override fun areItemsTheSame(oldItem: ClienteConPacchetti, newItem: ClienteConPacchetti) = oldItem.clienteld == newItem.clienteld
        override fun areContentsTheSame(oldItem: ClienteConPacchetti, newItem: ClienteConPacchetti) = oldItem == newItem
    }

    override fun getItemCount() = clienti.size

    fun submitList(newClienti: List<ClienteConPacchetti>) {
        clienti = newClienti
        notifyDataSetChanged()
    }
}