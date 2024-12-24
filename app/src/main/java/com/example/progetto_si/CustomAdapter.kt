package com.example.progetto_si

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class CustomAdapter(context: Context, private val items: List<String>) :
    ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, items) {
    private var filteredItems: List<String> = items.toList()

    override fun getCount(): Int {
        return filteredItems.size
    }

    override fun getItem(position: Int): String? {
        return filteredItems[position]
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                val results = if (query.isEmpty()) {
                    items
                } else {
                    items.filter { it.lowercase().contains(query) }
                }

                val filterResults = FilterResults()
                filterResults.values = results
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = results?.values as List<String>
                notifyDataSetChanged()
            }
        }
    }
}
