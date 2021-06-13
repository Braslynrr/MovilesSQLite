package Una.com.adapter

import una.com.logic.Student
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentViewModel:ViewModel(){
    private var items: MutableLiveData<List<Student>> = MutableLiveData()
    fun getItems(): LiveData<List<Student>> = items
    init {
        items= items
    }
}