package una.com.ui.student

import una.com.data.DBHelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import una.com.adapter.StudentAdapter
import una.com.databinding.FragmentStudentBinding

class Student : Fragment() {


    lateinit var list: RecyclerView
    lateinit var adapter: StudentAdapter
    lateinit var student:una.com.logic.Student
    var position = 0
    lateinit var mainSList: List<una.com.logic.Student>

    private var db: DBHelper? =null
    private var _binding: FragmentStudentBinding? = null
    private val binding: FragmentStudentBinding
        get() = _binding!!

    private fun initRecyclerView():StudentAdapter{
        val adapter= StudentAdapter()
        binding.applicationRecycle.adapter=adapter
        return adapter
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db= DBHelper(requireContext())
        mainSList= db!!.allStudents()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        list=binding.applicationRecycle
        list.setHasFixedSize(true)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun OnInitViewmodel(adapter: StudentAdapter) {
        db?.Slist?.observe(viewLifecycleOwner) { items ->
            adapter.items = items
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView().let { adapter -> OnInitViewmodel(adapter) }
    }

}
