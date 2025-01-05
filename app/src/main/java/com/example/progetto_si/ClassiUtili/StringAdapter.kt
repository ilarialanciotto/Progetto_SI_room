package com.example.progetto_si.ClassiUtili

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.Acquisti.AcquistiViewModel
import com.example.progetto_si.R
import kotlinx.coroutines.launch

class StringAdapter(
    private val data: List<String>,
    private var acqWM: AcquistiViewModel,
    private val onIconClick: (String) -> Unit// Funzione di callback per il click sull'icona
) : RecyclerView.Adapter<StringAdapter.ViewHolderOne>() {

    // ViewHolder per gestire gli elementi della lista
    class ViewHolderOne(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.txtP)
        val textAc : TextView = itemView.findViewById(R.id.Nacq)
        val btn : Button = itemView.findViewById(R.id.btn_dettagli)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOne {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_string, parent, false)
        return ViewHolderOne(view)
    }

    override fun onBindViewHolder(holder: ViewHolderOne, position: Int) {
        val item = data[position]
        holder.textView.text = item
        acqWM.getNumeroAcquisti(position+1) { n->
            holder.textAc.text=n.toString()
        }



       holder.btn.setOnClickListener {
           onIconClick(item)
       }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}

