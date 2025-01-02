package com.example.progetto_si.ClassiUtili

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.R

class AdapterStringNoedit(
    private val data: List<String>
) : RecyclerView.Adapter<AdapterStringNoedit.ViewHolderOne>() {

    // ViewHolder per gestire gli elementi della lista
    class ViewHolderOne(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textV1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOne {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notedit, parent, false)
        return ViewHolderOne(view)
    }

    override fun onBindViewHolder(holder: ViewHolderOne, position: Int) {
        val item = data[position]
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

