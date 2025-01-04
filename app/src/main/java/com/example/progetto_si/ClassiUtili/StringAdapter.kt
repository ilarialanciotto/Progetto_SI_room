package com.example.progetto_si.ClassiUtili

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_si.R

class StringAdapter(
    private val data: List<String>,
    private val onIconClick: (String) -> Unit // Funzione di callback per il click sull'icona
) : RecyclerView.Adapter<StringAdapter.ViewHolderOne>() {

    // ViewHolder per gestire gli elementi della lista
    class ViewHolderOne(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOne {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_string, parent, false)
        return ViewHolderOne(view)
    }

    override fun onBindViewHolder(holder: ViewHolderOne, position: Int) {
        val item = data[position]
        holder.textView.text = item

        // Gestisci il click sul drawableRight
        holder.textView.setOnTouchListener { v, event ->
            val drawableRight = holder.textView.compoundDrawables[2] // L'indice 2 è per il drawableRight
            if (drawableRight != null) {
                val x = event.x.toInt()
                val y = event.y.toInt()
                // Verifica se il click è nell'area del drawableRight
                if (x >= holder.textView.right - drawableRight.bounds.width()) {
                    // Gestisci il click sul drawableRight
                    onIconClick(item)
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

