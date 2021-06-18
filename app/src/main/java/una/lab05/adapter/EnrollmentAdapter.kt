package una.lab05.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import una.lab05.databinding.EnrollmentchardBinding
import una.lab05.logic.Enrollment

class EnrollmentAdapter : RecyclerView.Adapter<EnrollmentAdapter.ViewHolder>() {
    public var items = listOf<Enrollment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    public class ViewHolder(private val binding: EnrollmentchardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Enrollment) {
            binding.enrollment = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            EnrollmentchardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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