package com.job.wise.presentation.adapters

import android.icu.util.LocaleData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.job.wise.data.models.Job
import com.job.wise.databinding.ItemJobBinding
import java.text.SimpleDateFormat
import java.util.*

class JobListAdapter(
    val onItemClicked: (job: Job) -> Unit
) :
    ListAdapter<Job, JobListAdapter.JobViewHolder>(DiffCallback) {
    class JobViewHolder(private val binding: ItemJobBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(job: Job, companyName: String) {


            val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
            var formattedDate: String? = null
            job.appliedDate?.let {formattedDate = dateFormat.format(it) }
            binding.itemJobTitle.text = job.title
            binding.itemJobDate.text = formattedDate ?: "No Date Found"
                binding.itemJobCompany.text = companyName
        }
    }
    var map: Map<Job, String> = mutableMapOf()
    // Called before SubmitList
    fun submitMap(projectsMap : Map<Job, String>){
        map = projectsMap
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): JobViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding =
            ItemJobBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)


        return JobViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: JobViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val job = getItem(position)
        viewHolder.itemView.setOnClickListener {
            onItemClicked(job)
        }

        viewHolder.bind(job, map[job] ?: "No Company Found")
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Job>() {
            override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
                return oldItem.title == newItem.title && oldItem.description == newItem.description
            }
        }
    }

}