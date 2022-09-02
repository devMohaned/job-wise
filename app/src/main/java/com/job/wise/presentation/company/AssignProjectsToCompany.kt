package com.job.wise.presentation.company

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.job.wise.data.JobWiseApplication
import com.job.wise.databinding.FragmentAssignProjectsToCompanyBinding
import com.job.wise.databinding.ItemAssignProjectsBinding
import com.job.wise.presentation.adapters.ProjectListAdapter
import com.job.wise.viewmodels.company.CompanyViewModel
import com.job.wise.viewmodels.factory.CompanyViewModelFactory

class AssignProjectsToCompanyFragment : Fragment() {
    private var _binding: FragmentAssignProjectsToCompanyBinding? = null
    private val binding get() = _binding!!

    private lateinit var projectAssignAdapter: ProjectAssignAdapter


    private val args: AssignProjectsToCompanyFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAssignProjectsToCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        projectAssignAdapter = ProjectAssignAdapter(this, args.companyId)
        binding.pager.adapter = projectAssignAdapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            if (position % 2 == 0)
                tab.text = "Unassigned"
            else
                tab.text = "Assigned"
        }.attach()
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}

class ProjectAssignAdapter(fragment: Fragment, private val companyId: Int) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return ProjectAssignTabFragment(companyId, position % 2 == 1)
    }
}

class ProjectAssignTabFragment(private val companyId: Int, private val isAssignPage: Boolean) :
    Fragment() {
    private val viewModel: CompanyViewModel by viewModels {
        CompanyViewModelFactory(
            (activity?.application as JobWiseApplication).database.companyDao()
        )
    }

    private var _binding: ItemAssignProjectsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemAssignProjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter: ProjectListAdapter = ProjectListAdapter({}, {
            Toast.makeText(
                requireContext(),
                "Cannot Modify Here",
                Toast.LENGTH_LONG
            ).show()
        }, {
            if (isAssignPage) viewModel.unassignProject(it) else viewModel.assignProject(
                it,
                companyId
            )
        })

        binding.assignProjectsRecyclerView.adapter = adapter

        if (isAssignPage) {
            viewModel.retrieveProjectForCompany(companyId).observe(this.viewLifecycleOwner) {
                adapter.submitList(it)
            }
        } else {
            viewModel.retrieveUnassignedProjects().observe(this.viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}