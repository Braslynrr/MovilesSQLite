package una.lab05.ui.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import una.lab05.R
import una.lab05.data.DBHelper
import una.lab05.databinding.FragmentCourseBinding

class Course : Fragment() {
    private var db: DBHelper? =null
    private var _binding: FragmentCourseBinding? = null
    private val binding: FragmentCourseBinding
        get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root
    }
}