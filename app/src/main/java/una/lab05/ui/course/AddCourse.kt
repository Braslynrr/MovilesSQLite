package una.lab05.ui.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import una.lab05.R
import una.lab05.data.DBHelper
import una.lab05.databinding.FragmentAddCourseBinding
import una.lab05.logic.Course
import una.lab05.logic.Student

class AddCourse : Fragment() {
    private var db: DBHelper? =null
    private var _binding: FragmentAddCourseBinding? = null
    private val binding: FragmentAddCourseBinding
        get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db= DBHelper(requireContext())
        _binding = FragmentAddCourseBinding.inflate(inflater, container, false)
        var cu=arguments?.getInt("pos");
        if(cu==null){
            binding.addBtn.setOnClickListener {
                var cu=una.lab05.logic.Course(binding.NRCTxt.text.toString().toInt(),binding.descripcionTxt.text.toString(),binding.creditoTxt.text.toString().toInt())
                val not:Long=-1
                if( db?.insertCourse(cu)!=not){
                    toastMe("$cu Insetado!")
                    findNavController().navigate(R.id.course)
                }else{
                    toastMe("Algo falló, Revise sus campos o si el registro ya fue insertado")
                }
            }

        }else{
            var course: Course? = db?.Clist?.value?.get(cu);
            binding.addBtn.text = "Modificar"
            binding.NRCTxt.isEnabled=false
            binding.NRCTxt.setText(course?.ID.toString())
            binding.descripcionTxt.setText(course?.Descripcion)
            binding.creditoTxt.setText(course?.Creditos.toString())
            binding.addBtn.setOnClickListener {
                var cu=una.lab05.logic.Course(binding.NRCTxt.text.toString().toInt(),binding.descripcionTxt.text.toString(),binding.creditoTxt.text.toString().toInt())
                val not=-1
                if( db?.updateCourse(cu)!=not){
                    toastMe("${cu.ID} Modificado!")
                    findNavController().navigate(R.id.course)
                }else{
                    toastMe("Algo falló, Revise sus campos o si el registro no es modificable")
                }
            }
        }
        return binding.root
    }

    fun toastMe(message: String){
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

}