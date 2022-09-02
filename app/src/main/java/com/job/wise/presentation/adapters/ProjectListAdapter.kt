package com.job.wise.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.job.wise.data.models.Project
import com.job.wise.databinding.ItemProjectBinding

class ProjectListAdapter(
    val onItemClicked: (project: Project) -> Unit,
    val onItemModified: (project: Project) -> Unit,
    val onItemDeleted: (project: Project) -> Unit
) :
    ListAdapter<Project, ProjectListAdapter.ProjectViewHolder>(DiffCallback) {

    class ProjectViewHolder(private val binding: ItemProjectBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(project: Project) {

            binding.itemProjectName.text = project.name
            binding.itemProjectName.visibility = View.VISIBLE // Project Name Cannot be NULL

            binding.itemProjectDescription.visibility = if (project.description != null)
                View.VISIBLE
            else
                View.GONE
            binding.itemProjectDescription.text = project.description
            binding.itemProjectMadeTo.visibility =
                if(project.madeTo != null)
                    View.VISIBLE
                else
                    View.GONE
            binding.itemProjectMadeTo.text = project.madeTo


        }

        fun setOnItemModified(onItemModified: () -> Unit) {
            binding.itemProjectModify.setOnClickListener {
                onItemModified()
            }
        }
        fun setOnItemDeleted(onItemDeleted:() -> Unit) {
            binding.itemProjectDelete.setOnClickListener {
                onItemDeleted()
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ProjectViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding =
            ItemProjectBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)


        return ProjectViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ProjectViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val project = getItem(position)
        viewHolder.itemView.setOnClickListener {
            onItemClicked(project)
        }

        viewHolder.setOnItemModified { onItemModified(project) }
        viewHolder.setOnItemDeleted { onItemDeleted(project) }

        viewHolder.bind(project)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Project>() {
            override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

}