package una.com.adapter

import una.com.logic.Student
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import una.com.databinding.StudentchardBinding

class StudentAdapter:RecyclerView.Adapter<StudentAdapter.ViewHolder> (){
    public var items = listOf<Student>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    public  class ViewHolder(private val binding: StudentchardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: Student){
            binding.student=item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StudentchardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
}