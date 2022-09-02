package com.job.wise.presentation.jobs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.job.wise.data.JobWiseApplication
import com.job.wise.data.models.Company
import com.job.wise.data.models.Job
import com.job.wise.databinding.FragmentJobDetailsBinding
import com.job.wise.presentation.company.CompaniesFragmentDirections
import com.job.wise.viewmodels.factory.JobViewModelFactory
import com.job.wise.viewmodels.job.JobDetailsViewModel
import java.text.SimpleDateFormat
import java.util.*

class JobDetailsFragment : Fragment() {

    private val viewModel: JobDetailsViewModel by viewModels {
        JobViewModelFactory(
            (activity?.application as JobWiseApplication).database.jobDao()
        )
    }

    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var job: Job
    private val args: JobDetailsFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJobDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jobId = args.jobId
        val companyId = args.companyId

        binding.editJobButton.setOnClickListener {
            val action =
                JobDetailsFragmentDirections.actionJobDetailsFragmentToModifyJobFragment(jobId)
            findNavController().navigate(action)
        }



        viewModel.retrieveJob(jobId).observe(this.viewLifecycleOwner) { selectedJob ->
            job = selectedJob
            bind(job)
        }

        viewModel.retrieveCompany(companyId).observe(this.viewLifecycleOwner) { companyForJob ->
                bind(companyForJob)

        }
    }

    private fun bind(job: Job) {
        val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
        var formattedDate: String? = null
        job.appliedDate?.let {formattedDate = dateFormat.format(it) }

        binding.apply {
            jobTitle.text = job.title
            jobDescription.text = job.description
            jobRequirement.text = job.requirements
            jobNotes.text = job.notes
            appliedDate.text = formattedDate ?: "No Date Found"
        }

    }


    private fun bind(company: Company?) {
        binding.apply {
            jobCompanyName.text = company?.name ?: "No Company Found"
            jobCompanyRating.rating= company?.rating ?: 0f
        }

    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}