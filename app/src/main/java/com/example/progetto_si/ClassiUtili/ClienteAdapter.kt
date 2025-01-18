package com.example.progetto_si.ClassiUtili

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.Cliente.Room.Cliente
import androidx.lifecycle.lifecycleScope
import com.example.progetto_si.Acquisti.AcquistiViewModel
import com.example.progetto_si.R
import kotlinx.coroutines.launch
import kotlin.collections.get
import kotlin.toString

class ClienteAdapter(private var acqWM : AcquistiViewModel) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

    private var clienti: List<Cliente> = listOf()


    class ClienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeTextView: TextView = itemView.findViewById(R.id.txt_nome)
        private val emailTextView: TextView = itemView.findViewById(R.id.txt_email)
        val pacchettiTextView: TextView = itemView.findViewById(R.id.txt_numero_pacchetti)

        fun bind(cliente: Cliente) {
            nomeTextView.text = cliente.nome
            emailTextView.text = cliente.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = clienti[position]
        holder.bind(cliente)

        acqWM.getNumeroAcquistiCliente(cliente.id) { count ->
            holder.pacchettiTextView.text = count.toString()
        }
    }

    override fun getItemCount() = clienti.size

    fun submitList(newClienti: List<Cliente>) {
        clienti = newClienti
        notifyDataSetChanged()
    }
}