package com.job.wise.presentation.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.job.wise.R
import com.job.wise.data.JobWiseApplication
import com.job.wise.data.models.Project
import com.job.wise.databinding.FragmentModifyProjectBinding
import com.job.wise.viewmodels.project.ModifyProjectViewModel
import com.job.wise.viewmodels.factory.ProjectViewModelFactory

class ModifyProjectFragment : Fragment() {
    private val viewModel: ModifyProjectViewModel by viewModels {
        ProjectViewModelFactory(
            (activity?.application as JobWiseApplication).database.projectDao()
        )
    }
    lateinit var project: Project
    private var _binding: FragmentModifyProjectBinding? = null
    private val binding get() = _binding!!

    private val args: ModifyProjectFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentModifyProjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = args.projectId
        if (id > 0) {
            viewModel.retrieveProject(id).observe(this.viewLifecycleOwner) { selectedProject ->
                project = selectedProject
                bind(project)
            }
        } else {
            binding.saveButton.setOnClickListener {
                addNewItem()
            }


        }
    }



    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.nameTextField.editText?.text.toString()
        )
    }

    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewProject(
                binding.nameTextField.editText?.text.toString(),
                binding.descriptionTextField.editText?.text.toString(),
                binding.madeToTextField.editText?.text.toString()
            )
            findNavController().navigate(R.id.action_modifyProjectFragment_to_projectsFragment)
        }
    }

    private fun bind(project: Project){
        binding.apply {
            nameTextField.editText?.setText(project.name)
            descriptionTextField.editText?.setText(project.description, TextView.BufferType.SPANNABLE)
            madeToTextField.editText?.setText(project.madeTo)
            saveButton.setOnClickListener { updateProject() }

        }
    }

    private fun updateProject() {
        if (isEntryValid()) {
            viewModel.updateProject(
                this.args.projectId,
                this.binding.nameTextField.editText?.text.toString(),
                this.binding.descriptionTextField.editText?.text.toString(),
                this.binding.madeToTextField.editText?.text.toString()
            )
            val action = ModifyProjectFragmentDirections.actionModifyProjectFragmentToProjectsFragment()
            findNavController().navigate(action)}
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}