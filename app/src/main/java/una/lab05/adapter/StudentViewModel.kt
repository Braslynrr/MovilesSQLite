package una.lab05.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import una.lab05.logic.Student

class StudentViewModel: ViewModel(){
    private var items: MutableLiveData<List<Student>> = MutableLiveData()
    fun getItems(): LiveData<List<Student>> = items
    init {
        items= items
    }
}