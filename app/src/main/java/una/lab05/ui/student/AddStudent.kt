package una.lab05.ui.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import una.lab05.R
import una.lab05.data.DBHelper
import una.lab05.databinding.FragmentAddStudentBinding
import una.lab05.logic.Student

class AddStudent : Fragment() {
    lateinit var db:DBHelper
    private var _binding: FragmentAddStudentBinding? = null
    private val binding: FragmentAddStudentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        db= DBHelper(requireContext())

        var st=arguments?.getInt("pos");

        if(st==null){

            binding.addBtn.setOnClickListener {
                var st= una.lab05.logic.Student(binding.idTxt.text.toString().toInt(),binding.nombretxt.text.toString(),binding.apellidoTxt.text.toString(),binding.edadTxt.text.toString().toInt())
                val not:Long=-1
                if( db.insertStudent(st)!=not){
                    toastMe("Insetado!")
                    findNavController().navigate(R.id.student)
                }else{
                    toastMe("Algo falló, Revise sus campos o si el registro ya fue insertado")
                }
            }

        }else{

            var student: Student? = db.Slist.value?.get(st);
            binding.addBtn.setText("Modificar")
            binding.idTxt.isEnabled=false
            binding.idTxt.setText(student?.ID.toString())
            binding.nombretxt.setText(student?.Nombre)
            binding.apellidoTxt.setText(student?.Apellido)
            binding.edadTxt.setText(student?.Edad.toString())

            binding.addBtn.setOnClickListener {
                var st= una.lab05.logic.Student(binding.idTxt.text.toString().toInt(),binding.nombretxt.text.toString(),binding.apellidoTxt.text.toString(),binding.edadTxt.text.toString().toInt())
                val not=-1
                if( db.updateStudent(st)!=not){
                    toastMe("Modificado!")
                    findNavController().navigate(R.id.student)
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