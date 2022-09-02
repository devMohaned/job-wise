package com.job.wise.presentation.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.job.wise.data.JobWiseApplication
import com.job.wise.data.models.Project
import com.job.wise.databinding.FragmentProjectDetailsBinding
import com.job.wise.viewmodels.project.ProjectDetailsViewModel
import com.job.wise.viewmodels.factory.ProjectViewModelFactory

class ProjectDetailsFragment : Fragment() {
    private val viewModel: ProjectDetailsViewModel by viewModels {
        ProjectViewModelFactory(
            (activity?.application as JobWiseApplication).database.projectDao()
        )
    }


    lateinit var project: Project
    private var _binding: FragmentProjectDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: ProjectDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProjectDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.projectId
        viewModel.retrieveProject(id).observe(this.viewLifecycleOwner) { selectedProject ->
            project = selectedProject
            bind(project)
        }
    }

    private fun bind(project: Project) {
        binding.apply {
            projectTitle.text = project.name
            projectDescription.text = if(project.description.isNullOrEmpty()) "No Description Found" else project.description
            projectMadeTo.text = if(project.madeTo.isNullOrEmpty())
                "Empty" else project.madeTo
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}