package com.job.wise.presentation.jobs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.job.wise.R
import com.job.wise.data.JobWiseApplication
import com.job.wise.data.models.Project
import com.job.wise.databinding.FragmentJobsBinding
import com.job.wise.presentation.adapters.CompanyListAdapter
import com.job.wise.presentation.adapters.JobListAdapter
import com.job.wise.presentation.company.CompaniesFragmentDirections
import com.job.wise.viewmodels.factory.JobViewModelFactory
import com.job.wise.viewmodels.factory.ProjectViewModelFactory
import com.job.wise.viewmodels.job.JobViewModel
import com.job.wise.viewmodels.project.ProjectViewModel

class JobsFragment : Fragment() {

    private val viewModel: JobViewModel by viewModels {
        JobViewModelFactory(
            (activity?.application as JobWiseApplication).database.jobDao()
        )
    }

    private var _binding: FragmentJobsBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addNewJobButton.setOnClickListener {
            val action = JobsFragmentDirections.actionJobsAppliedFragmentToModifyJobFragment()
            findNavController().navigate(action)
        }

        val adapter: JobListAdapter = JobListAdapter{
            val action = JobsFragmentDirections.actionJobsAppliedFragmentToJobDetailsFragment(it.id, it.companyAppliedId)
            findNavController().navigate(action)
        }

        binding.jobsRecyclerView.adapter = adapter

        binding.jobsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        viewModel.allJobs.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitMap(it)
                adapter.submitList(it.keys.toList())
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}