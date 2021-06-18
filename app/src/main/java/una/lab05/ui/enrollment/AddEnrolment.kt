package una.lab05.ui.enrollment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import una.lab05.R
import una.lab05.data.DBHelper
import una.lab05.databinding.FragmentAddEnrolmentBinding
import java.lang.Exception


class AddEnrolment : Fragment() {
    private var db: DBHelper? =null
    private var _binding: FragmentAddEnrolmentBinding? = null
    private val binding:  FragmentAddEnrolmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentAddEnrolmentBinding.inflate(inflater, container, false)
        db= DBHelper(requireContext())
        var cu=arguments?.getInt("pos");
        if(cu==null){
            var adapter= ArrayAdapter( requireContext(),
                android.R.layout.simple_spinner_item, db!!.allCourses().map {x->"$x"})
            binding.SNRC.adapter=adapter
            adapter=ArrayAdapter( requireContext(),
                android.R.layout.simple_spinner_item, db!!.allStudents().map {x->"${x.ID}"})
            binding.IDTxt.setAdapter(adapter)

            binding.matriculaBtn.setOnClickListener {
               var NRC= binding.SNRC.selectedItem.toString()
                NRC=NRC.split(" ")[0]
                try{
                    var student= db!!.findStudent(binding.IDTxt.text.toString())
                    var course=db!!.findCourse(NRC)
                    if (db!!.Elist.value?.filter { x-> x.Student.ID==student.ID && x.course.ID==course.ID }?.size==1)
                        throw Exception("Ya está Matriculado!")
                    var en=una.lab05.logic.Enrollment(0,student,course)
                    val not:Long=-1
                    if( db?.insertEnrollment(en)!=not){
                        toastMe("$en Insetado!")
                        findNavController().navigate(R.id.enrollment)
                    }else{
                        toastMe("Algo falló")
                    }
                }catch (e:Exception){
                    if(e.message=="Ya está Matriculado!"){
                        toastMe(e.message.toString())
                    }else{
                        toastMe("Alguno de los dos registros no existe")
                    }
                }
            }
        }
        return binding.root
    }
    fun toastMe(message: String){
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }
}