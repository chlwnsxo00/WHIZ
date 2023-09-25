import android.app.Application
import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.adapter.SiteListAdapter
import com.example.room.SiteRoomDatabase
import com.example.viewModels.SiteViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.file.Files.delete

class DragManageAdapter(
    private val siteListAdapter: SiteListAdapter, // Use the adapter as a parameter
    private val context: Context,
    dragDirs: Int,
    swipeDirs: Int
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    // Existing functions...

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        siteListAdapter.swapItems(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val deletedSite = siteListAdapter.currentList[viewHolder.adapterPosition]
        val position = viewHolder.adapterPosition

//        // Delete the site from the database
//        GlobalScope.launch(Dispatchers.IO) {
//            SiteRoomDatabase.Site
//        }

        siteListAdapter.notifyItemRemoved(position)

        Snackbar.make(
            viewHolder.itemView,
            "삭제됨 : " + deletedSite.name,
            Snackbar.LENGTH_LONG
        ).setAction("되돌리기") {
            // You can add the undo logic here, but you'll need to insert the deletedSite back
            // into the database using siteDao.insert(deletedSite) and update the adapter.
        }.show()
    }

}
