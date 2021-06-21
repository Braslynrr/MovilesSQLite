package una.lab05.ui.enrollment

import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import una.lab05.R
import una.lab05.adapter.EnrollmentAdapter
import una.lab05.data.DBHelper
import una.lab05.databinding.FragmentEnrollmentBinding
import java.util.*


class Enrollment : Fragment() {
    lateinit var list: RecyclerView
    lateinit var adapter: EnrollmentAdapter
    lateinit var student: Enrollment
    lateinit var mainList: List<una.lab05.logic.Enrollment>
    var position = 0

    private var db: DBHelper? =null
    private var _binding: FragmentEnrollmentBinding? = null
    private val binding: FragmentEnrollmentBinding
        get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentEnrollmentBinding.inflate(inflater, container, false)
        db= DBHelper(requireContext())
        mainList= db!!.allEnrollment()
        list=binding.applicationRecycle
        list.setHasFixedSize(true)

        binding.searchBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                db?.Elist?.value=mainList
                db?.filterEnrollment(newText)
                return false
            }

        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPosition: Int = viewHolder.adapterPosition
                val toPosition: Int = target.adapterPosition

                val listaMove : List<una.lab05.logic.Enrollment>? = db?.Elist?.value
                Collections.swap(listaMove, fromPosition, toPosition)
                db?.Elist?.value = listaMove

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                position = viewHolder.adapterPosition

                if(direction == ItemTouchHelper.LEFT){

                    val enrollment = db!!.Elist.value!!.get(position)

                    db?.deleteEnrollment(enrollment)

                    Snackbar.make(list,"Se eliminaría ${enrollment}", Snackbar.LENGTH_LONG).setAction("Undo") {
                        db?.insertEnrollment(enrollment)
                    }.show()
                    adapter.items=db!!.allEnrollment()
                }
            }

            override fun onChildDraw(c: Canvas, recyclerView : RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                if (dX<0){
                    RecyclerViewSwipeDecorator.Builder(activity , c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(R.color.red)
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                        .create()
                        .decorate()
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(list)

        return binding.root
    }

    private fun initRecyclerView():EnrollmentAdapter{
        adapter= EnrollmentAdapter()
        binding.applicationRecycle.adapter=adapter
        return adapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        db=null
    }

    private fun OnInitViewmodel(adapter: EnrollmentAdapter) {
        db?.Elist?.observe(viewLifecycleOwner) { items ->
            adapter.items = items
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView().let { adapter -> OnInitViewmodel(adapter) }
    }
}