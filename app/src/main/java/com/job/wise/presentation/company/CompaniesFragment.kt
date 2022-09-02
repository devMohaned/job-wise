package com.job.wise.presentation.company

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.job.wise.data.JobWiseApplication
import com.job.wise.data.models.Company
import com.job.wise.data.models.Project
import com.job.wise.databinding.FragmentCompaniesBinding
import com.job.wise.presentation.adapters.CompanyListAdapter
import com.job.wise.presentation.project.ProjectsFragmentDirections
import com.job.wise.viewmodels.company.CompanyViewModel
import com.job.wise.viewmodels.factory.CompanyViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CompaniesFragment : Fragment() {
    private val viewModel: CompanyViewModel by viewModels {
        CompanyViewModelFactory(
            (activity?.application as JobWiseApplication).database.companyDao()
        )
    }

    lateinit var project: Project
    private var _binding: FragmentCompaniesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompaniesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addNewCompanyButton.setOnClickListener {
            val action = CompaniesFragmentDirections.actionCompaniesFragmentToModifyCompanyFragment()
            findNavController().navigate(action)
        }

        val adapter: CompanyListAdapter = CompanyListAdapter{
            val action = CompaniesFragmentDirections.actionCompaniesFragmentToCompanyDetailsFragment(it.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.adapter = adapter

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        viewModel.allCompanies.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitMap(it)
                adapter.submitList(it.keys.toList())
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}