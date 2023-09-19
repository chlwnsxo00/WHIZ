package com.example.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.Interface.ItemListener
import com.example.obj.sites
import com.example.pin.R

class NameAdapter(val nameList : ArrayList<sites>, val listener : ItemListener) :
    RecyclerView.Adapter<NameAdapter.NameViewHolder>() {

    /**
     * Creator function
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder
    {
        val view = LayoutInflater.from(parent?.getContext())
            .inflate(R.layout.sites, parent, false);

        //return ViewHolder
        return NameViewHolder(view)
    }

    /**
     * Binder function
     */
    override fun onBindViewHolder(holder: NameViewHolder, position: Int)
    {
        holder.bindData(sites(nameList.get(position).name,nameList.get(position).url), listener)
    }

    /**
     * Returns item counts
     * or list size
     */
    override fun getItemCount(): Int
    {
        return nameList.size
    }

    class NameViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var tvName = itemView?.findViewById<TextView>(R.id.tvName)
        val touch = itemView?.findViewById<ConstraintLayout>(R.id.touch)
        fun bindData(site: sites, listener: ItemListener) {
            tvName?.text = site.name
            touch?.setOnClickListener { itemView ->
                listener.onClicked(site)
            }
        }
    }
    fun swapItems(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition..toPosition - 1) {
                nameList.set(i, nameList.set(i+1, nameList.get(i)));
            }
        } else {
            for (i in fromPosition..toPosition + 1) {
                nameList.set(i, nameList.set(i-1, nameList.get(i)));
            }
        }

        notifyItemMoved(fromPosition, toPosition)
    }
}