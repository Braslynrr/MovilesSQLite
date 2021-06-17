package una.lab05.ui.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import una.lab05.R
import una.lab05.data.DBHelper
import una.lab05.databinding.FragmentAddCourseBinding

class AddCourse : Fragment() {
    private var db: DBHelper? =null
    private var _binding: FragmentAddCourseBinding? = null
    private val binding: FragmentAddCourseBinding
        get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCourseBinding.inflate(inflater, container, false)
        return binding.root
    }
}