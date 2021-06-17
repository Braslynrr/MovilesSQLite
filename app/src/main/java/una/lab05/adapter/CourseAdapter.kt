package una.lab05.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import una.lab05.databinding.CoursechardBinding
import una.lab05.logic.Course

class CourseAdapter: RecyclerView.Adapter<CourseAdapter.ViewHolder>() {
    public var items = listOf<Course>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    public  class ViewHolder(private val binding: CoursechardBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Course){
            binding.course=item
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CoursechardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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