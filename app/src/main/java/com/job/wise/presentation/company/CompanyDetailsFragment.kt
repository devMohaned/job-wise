package com.job.wise.presentation.company

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.job.wise.R
import com.job.wise.data.JobWiseApplication
import com.job.wise.data.models.Company
import com.job.wise.data.models.Project
import com.job.wise.databinding.FragmentCompaniesBinding
import com.job.wise.databinding.FragmentCompanyDetailsBinding
import com.job.wise.presentation.adapters.ProjectListAdapter
import com.job.wise.presentation.project.ProjectDetailsFragmentArgs
import com.job.wise.presentation.project.ProjectsFragmentDirections
import com.job.wise.viewmodels.company.CompanyDetailsViewModel
import com.job.wise.viewmodels.company.CompanyViewModel
import com.job.wise.viewmodels.factory.CompanyViewModelFactory

class CompanyDetailsFragment : Fragment() {
    private val viewModel: CompanyDetailsViewModel by viewModels {
        CompanyViewModelFactory(
            (activity?.application as JobWiseApplication).database.companyDao()
        )
    }

    lateinit var company: Company
    private var _binding: FragmentCompanyDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: CompanyDetailsFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompanyDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.companyId

        binding.companyEditFloatingActionBar.setOnClickListener {
            val action = CompanyDetailsFragmentDirections.actionCompanyDetailsFragmentToModifyCompanyFragment(id)
            findNavController().navigate(action)
        }
        binding.companyModifyProjectsButton.setOnClickListener {
            val action = CompanyDetailsFragmentDirections.actionCompanyDetailsFragmentToAssignProjectsToCompanyFragment(id)
            findNavController().navigate(action)
        }

        val adapter = ProjectListAdapter({}, {
            val action = CompanyDetailsFragmentDirections.actionCompanyDetailsFragmentToAssignProjectsToCompanyFragment(id)
            findNavController().navigate(action)
        }, {Toast.makeText(requireContext(), "Cannot delete here", Toast.LENGTH_LONG).show()}
        )
        binding.companyRecyclerView.adapter = adapter

        binding.companyRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        viewModel.retrieveCompany(id).observe(this.viewLifecycleOwner) { selectedCompany ->

            company = selectedCompany
            bind(company)
        }

        viewModel.retrieveCompanyProjects(id).observe(this.viewLifecycleOwner){items ->
            items.let {
                adapter.submitList(it)
            }
        }
    }

    private fun bind(company: Company) {
        binding.apply {
            companyTitleTextView.text = company.name
            companyRatingBar.rating = company.rating ?: 0f
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}