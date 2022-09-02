package com.job.wise.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.job.wise.data.models.Company
import com.job.wise.data.models.Project
import com.job.wise.databinding.ItemCompanyBinding
import com.job.wise.databinding.ItemProjectBinding

class CompanyListAdapter(
    val onItemClicked: (company: Company) -> Unit
) :
    ListAdapter<Company, CompanyListAdapter.CompanyViewHolder>(DiffCallback) {
    var map: Map<Company, Int> = mutableMapOf()
    class CompanyViewHolder(private val binding: ItemCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(company: Company, projectCount: Int) {

            binding.companyTitleTextView.text = "${company.name} (${company.rating} stars)"
            binding.companyCountProjectsTextView.text = "$projectCount Projects Saved"
            binding.companyRating.rating = company.rating ?: 0f
        }
    }

    // Called before SubmitList
    fun submitMap(projectsMap : Map<Company, Int>){
        map = projectsMap
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CompanyViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding =
            ItemCompanyBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)


        return CompanyViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: CompanyViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val company = getItem(position)
        viewHolder.itemView.setOnClickListener {
            onItemClicked(company)
        }

        viewHolder.bind(company, map[company] ?: 0)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Company>() {
            override fun areItemsTheSame(oldItem: Company, newItem: Company): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Company, newItem: Company): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

}