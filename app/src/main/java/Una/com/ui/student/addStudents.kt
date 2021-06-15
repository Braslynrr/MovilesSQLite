package una.com.ui.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import una.com.data.DBHelper
import una.com.databinding.FragmentAddStudentsBinding

class addStudents : Fragment() {
    lateinit var db:DBHelper
    private var _binding: FragmentAddStudentsBinding? = null
    private val binding: FragmentAddStudentsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddStudentsBinding.inflate(inflater, container, false)
        db= DBHelper(requireContext())
        binding.addBtn.setOnClickListener {
            var st= una.com.logic.Student(binding.idTxt.text.toString().toInt(),binding.nombretxt.text.toString(),binding.apellidoTxt.text.toString(),binding.edadTxt.text.toString().toInt())
            db.insertStudent(st)
        }
        return binding.root
    }

}