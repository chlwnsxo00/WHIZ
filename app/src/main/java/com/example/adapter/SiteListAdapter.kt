package com.example.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.Interface.ItemListener
import com.example.db.SiteRepository
import com.example.pin.R
import com.example.room.Site

class SiteListAdapter(private val listener : ItemListener) : ListAdapter<Site, SiteListAdapter.WordViewHolder>(WordsComparator()) {

    // Add this callback property
    var onItemMovedCallback: ((Int, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current,listener)
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val siteItemView: TextView = itemView.findViewById(R.id.tvName)
        val touch : ConstraintLayout? = itemView.findViewById(com.example.pin.R.id.touch)
        fun bind(site: Site, listener: ItemListener) {
            siteItemView.text = site.name
            touch?.setOnClickListener { itemView ->
                listener.onClicked(site)
            }
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.sites, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Site>() {
        override fun areItemsTheSame(oldItem: Site, newItem: Site): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Site, newItem: Site): Boolean {
            return oldItem.name == newItem.name
        }
    }


    fun swapItems(fromPosition: Int, toPosition: Int) {
        val fromItem = getItem(fromPosition)
        val toItem = getItem(toPosition)

        // Swap the items in your data source (e.g., the list of Sites)
        currentList[fromPosition] = toItem
        currentList[toPosition] = fromItem

        // Notify the adapter of the item movement
        notifyItemMoved(fromPosition, toPosition)

        // Notify the callback about the item movement
        onItemMovedCallback?.invoke(fromPosition, toPosition)
    }
}