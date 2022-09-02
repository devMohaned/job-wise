package com.job.wise.presentation.company

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.job.wise.R
import com.job.wise.data.JobWiseApplication
import com.job.wise.data.models.Company
import com.job.wise.data.models.Project
import com.job.wise.databinding.FragmentModifyCompanyBinding
import com.job.wise.viewmodels.company.ModifyCompanyViewModel
import com.job.wise.viewmodels.factory.CompanyViewModelFactory
import com.job.wise.viewmodels.factory.ProjectViewModelFactory

class ModifyCompanyFragment : Fragment() {
    private val viewModel: ModifyCompanyViewModel by viewModels {
        CompanyViewModelFactory(
            (activity?.application as JobWiseApplication).database.companyDao()
        )
    }
    lateinit var company: Company
    private var _binding: FragmentModifyCompanyBinding? = null
    private val binding get() = _binding!!

    private val args: ModifyCompanyFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentModifyCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = args.companyId
        if (id > 0) {
            viewModel.retrieveCompany(id).observe(this.viewLifecycleOwner) { selectedCompany ->
                company = selectedCompany
                bind(company)
            }

            binding.deleteButton.setOnClickListener {
                viewModel.deleteCompany(company)
                val action = ModifyCompanyFragmentDirections.actionModifyCompanyFragmentToCompaniesFragment()
                findNavController().navigate(action)
            }
        } else {
            binding.saveButton.setOnClickListener {
                addNewItem()
            }
            binding.deleteButton.visibility = View.GONE


        }
    }



    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.nameTextField.editText?.text.toString(),
            binding.companyRatingBar.rating
        )
    }

    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewCompany(
                binding.nameTextField.editText?.text.toString(),
                binding.companyRatingBar.rating
            )
            val action = ModifyCompanyFragmentDirections.actionModifyCompanyFragmentToCompaniesFragment()
            findNavController().navigate(action)
        }
    }

    private fun bind(company: Company){
        binding.apply {
            nameTextField.editText?.setText(company.name)
            binding.companyRatingBar.rating = company.rating ?: 0f

            saveButton.setOnClickListener { updateProject() }

        }
    }

    private fun updateProject() {
        if (isEntryValid()) {
            viewModel.updateCompany(
                this.args.companyId,
                this.binding.nameTextField.editText?.text.toString(),
                binding.companyRatingBar.rating
            )
            val action = ModifyCompanyFragmentDirections.actionModifyCompanyFragmentToCompaniesFragment()
            findNavController().navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}