package una.lab05.ui.course

import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import una.lab05.R
import una.lab05.adapter.CourseAdapter
import una.lab05.data.DBHelper
import una.lab05.databinding.FragmentCourseBinding
import una.lab05.logic.Course
import java.util.*

class Course : Fragment() {

    lateinit var list: RecyclerView
    lateinit var course: Course
    var position = 0
    lateinit var mainlist:List<una.lab05.logic.Course>
    private var db: DBHelper? =null
    private var _binding: FragmentCourseBinding? = null
    private val binding: FragmentCourseBinding
        get() = _binding!!

    private fun initRecyclerView(): CourseAdapter {
        val adapter= CourseAdapter()
        binding.applicationRecycle.adapter=adapter
        return adapter
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db= DBHelper(requireContext())
        mainlist= db!!.allCourses()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCourseBinding.inflate(inflater, container, false)
        list=binding.applicationRecycle
        list.setHasFixedSize(true)
        //Filter
        binding.searchBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                db?.Clist?.value=mainlist
                db?.filterCourse(newText)
                return false
            }

        })


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPosition: Int = viewHolder.adapterPosition
                val toPosition: Int = target.adapterPosition

                val listaMove : List<una.lab05.logic.Course>? = db?.Clist?.value
                Collections.swap(listaMove, fromPosition, toPosition)
                db?.Clist?.value = listaMove

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                position = viewHolder.adapterPosition
                var quien= ""

                if(direction == ItemTouchHelper.LEFT){

                    val course = db!!.Clist.value!!.get(position)

                    db?.deleteCourse(course)

                    Snackbar.make(list,"Se eliminaría $course", Snackbar.LENGTH_LONG).setAction("Undo") {
                        db?.insertCourse(course)
                    }.show()

                }else{
                    val bundle = bundleOf("pos" to (position))
                    view!!.findNavController().navigate(R.id.addCourse,bundle)
                }
            }

            override fun onChildDraw(c: Canvas, recyclerView : RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                RecyclerViewSwipeDecorator.Builder(activity , c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(R.color.red)
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(R.color.green)
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(list)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        db=null
    }

    private fun OnInitViewmodel(adapter: CourseAdapter) {
        db?.Clist?.observe(viewLifecycleOwner) { items ->
            adapter.items = items
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView().let { adapter -> OnInitViewmodel(adapter) }
    }

}