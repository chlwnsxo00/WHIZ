
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.example.Interface.ItemListener
import com.example.obj.MenuTitle
import com.example.pin.R
import com.example.pin.databinding.ItemChildBinding
import com.example.pin.databinding.ItemParentBinding
import com.example.room.Site

class ExpandableListAdapter(
    private val context: Context,
    private val parents: MutableList<MenuTitle>,
    private val childList: MutableList<MutableList<Site>>,
    private val listener : ItemListener
) : BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return parents.size
    }

    override fun getChildrenCount(parent: Int): Int {
        return childList[parent].size
    }

    override fun getGroup(parent: Int): Any {
        return parents[parent]
    }

    override fun getChild(parent: Int, child: Int): Any {
        return childList[parent][child]
    }

    override fun getGroupId(parent: Int): Long {
        return parent.toLong()
    }

    override fun getChildId(parent: Int, child: Int): Long {
        return child.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(parent: Int, isExpanded: Boolean, convertView: View?, parentView: ViewGroup?): View {
        val binding = ItemParentBinding.inflate(LayoutInflater.from(context), parentView, false)
        binding.expandTitle.text= parents[parent].title
        setArrow(binding, isExpanded)

        return binding.root
    }

    override fun getChildView(parent: Int, child: Int, isLastChild: Boolean, convertView: View?, parentView: ViewGroup?): View {
        val binding = ItemChildBinding.inflate(LayoutInflater.from(context), parentView, false)
        val item = getChild(parent, child) as Site

        binding.expandChildTitle.text= item.name
        listener.onClicked(item)
        return binding.root
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }
    fun setArrow(binding: ItemParentBinding, isExpanded: Boolean) {
        if(isExpanded) {
            binding.explainIcon.setImageResource(R.drawable.arrow_up)
        } else {
            binding.explainIcon.setImageResource(R.drawable.arrow_down)
        }
    }
}