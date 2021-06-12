package Una.com.ui.student

import Una.com.R
import Una.com.data.DBHelper
import Una.com.databinding.FragmentStudentBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class Student : Fragment() {
    private var db: DBHelper? =null
    private var _binding: FragmentStudentBinding? = null
    private val binding: FragmentStudentBinding
        get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db= DBHelper(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        handleInsert()
        handleUpdate()
        handleDelete()
        handleFind()
        handleShowAll()
        return binding.root
    }

    private fun handleShowAll() {

    }

    private fun handleFind() {

    }

    private fun handleDelete() {

    }

    private fun handleUpdate() {

    }

    private fun handleInsert() {

    }
}
