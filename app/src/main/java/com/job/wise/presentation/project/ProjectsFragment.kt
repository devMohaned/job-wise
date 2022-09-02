package com.job.wise.presentation.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.job.wise.R
import com.job.wise.data.JobWiseApplication
import com.job.wise.data.models.Project
import com.job.wise.databinding.FragmentProjectsBinding
import com.job.wise.presentation.adapters.ProjectListAdapter
import com.job.wise.viewmodels.project.ProjectViewModel
import com.job.wise.viewmodels.factory.ProjectViewModelFactory


class ProjectsFragment : Fragment() {
    private val viewModel: ProjectViewModel by viewModels {
        ProjectViewModelFactory(
            (activity?.application as JobWiseApplication).database.projectDao()
        )
    }

    lateinit var project: Project
    private var _binding: FragmentProjectsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addNewProjectButton.setOnClickListener {
            val action = ProjectsFragmentDirections.actionProjectsFragmentToModifyProjectFragment()
            findNavController().navigate(action)
        }

        val adapter = ProjectListAdapter({ project ->
            findNavController().navigate(
                ProjectsFragmentDirections.actionProjectsFragmentToProjectDetailsFragment(
                    project.id
                )
            )
        }, { project ->
            findNavController().navigate(
                ProjectsFragmentDirections.actionProjectsFragmentToModifyProjectFragment(getString(R.string.edit_project),project.id)
            )
        }, { project ->
            viewModel.deleteProject(project)
        }
        )
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        viewModel.allProjects.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}