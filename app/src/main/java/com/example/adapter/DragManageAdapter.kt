package com.example.adapter

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.obj.sites
import com.google.android.material.snackbar.Snackbar

class DragManageAdapter(
    private val nameAdapter: NameAdapter,
    private val nameList: ArrayList<sites>,
    private val context: Context,
    dragDirs: Int,
    swipeDirs: Int
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        nameAdapter.swapItems(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val deletedSite: sites = nameList[viewHolder.adapterPosition]
        val position = viewHolder.adapterPosition

        nameList.removeAt(position)
        nameAdapter.notifyItemRemoved(position)

        Snackbar.make(
            viewHolder.itemView,
            "Deleted " + deletedSite.name,
            Snackbar.LENGTH_LONG
        ).setAction("Undo") {
            nameList.add(position, deletedSite)
            nameAdapter.notifyItemInserted(position)
        }.show()
    }
}
