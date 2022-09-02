package com.job.wise.presentation.jobs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.job.wise.data.JobWiseApplication
import com.job.wise.data.models.Company
import com.job.wise.data.models.Job
import com.job.wise.databinding.FragmentModifyJobBinding
import com.job.wise.presentation.adapters.CompanySpinnerAdapter
import com.job.wise.viewmodels.factory.JobViewModelFactory
import com.job.wise.viewmodels.job.ModifyJobViewModel
import java.text.SimpleDateFormat
import java.util.*


class ModifyJobFragment : Fragment() {
    private val viewModel: ModifyJobViewModel by viewModels {
        JobViewModelFactory(
            (activity?.application as JobWiseApplication).database.jobDao()
        )
    }
    lateinit var job: Job
    private var _binding: FragmentModifyJobBinding? = null
    private val binding get() = _binding!!

    private var companyId: Int = 0
    private val args: ModifyJobFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentModifyJobBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.jobId
        if (id > 0) {
            viewModel.retrieveJob(id).observe(this.viewLifecycleOwner) { selectedJob ->
                job = selectedJob
                companyId = job.companyAppliedId
                bind(job)
            }

            binding.deleteButton.setOnClickListener {
                viewModel.deleteJob(job)
                val action =
                    ModifyJobFragmentDirections.actionModifyJobFragmentToJobsAppliedFragment()
                findNavController().navigate(action)
            }

            viewModel.availableCompanies.observe(this.viewLifecycleOwner) { companies ->
                companies.let {

                    val spinnerArrayAdapter: CompanySpinnerAdapter = CompanySpinnerAdapter(
                        requireContext(),
                        companies,

                        )
                    binding.jobCompanySpinner.adapter = spinnerArrayAdapter
                }
               val selectedPosition = companies.indexOfFirst { it.id == companyId }
                binding.jobCompanySpinner.setSelection(selectedPosition)


            }

        } else {
            binding.saveButton.setOnClickListener {
                addNewItem()
            }
            binding.deleteButton.visibility = View.GONE

            viewModel.availableCompanies.observe(this.viewLifecycleOwner) { companies ->
                Log.e("Tag", "Best")
                companies.let {

                    companyId = companies[0].id
                    val spinnerArrayAdapter: CompanySpinnerAdapter = CompanySpinnerAdapter(
                        requireContext(),
                        companies,
                    )
                    binding.jobCompanySpinner.adapter = spinnerArrayAdapter
                }
            }
        }
        binding.jobCompanySpinner.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem: Company = parent.getItemAtPosition(position) as Company
                companyId = selectedItem.id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }


    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.jobTitleTextField.editText?.text.toString()
        )
    }

    private fun addNewItem() {
        if (isEntryValid()) {
            val cal = Calendar.getInstance()
            cal[Calendar.YEAR] = binding.appliedDatePicker.year
            cal[Calendar.MONTH] = binding.appliedDatePicker.month
            cal[Calendar.DAY_OF_MONTH] = binding.appliedDatePicker.dayOfMonth
            val appliedDate = cal.time
            viewModel.addNewJob(
                binding.jobTitleTextField.editText?.text.toString(),
                binding.jobDescriptionTextField.editText?.text.toString(),
                binding.jobRequirementTextField.editText?.text.toString(),
                appliedDate,
                binding.jobNotesTextField.editText?.text.toString(),
                companyId,
            )
            val action = ModifyJobFragmentDirections.actionModifyJobFragmentToJobsAppliedFragment()
            findNavController().navigate(action)
        }
    }

    private fun bind(job: Job) {
        val sdf = SimpleDateFormat("yyyy/mm/dd", Locale.ENGLISH)
        sdf.format(job.appliedDate ?: Calendar.getInstance())
        val cal = sdf.calendar

        binding.apply {
            jobTitleTextField.editText?.setText(job.title)
            jobDescriptionTextField.editText?.setText(job.description)
            jobRequirementTextField.editText?.setText(job.requirements)
            jobNotesTextField.editText?.setText(job.notes)
            appliedDatePicker.updateDate(
                cal[Calendar.YEAR],
                cal[Calendar.MONTH],
                cal[Calendar.DAY_OF_MONTH]
            )
            saveButton.setOnClickListener { updateProject() }
        }


    }

    private fun updateProject() {
        if (isEntryValid()) {
            val cal = Calendar.getInstance()
            cal[Calendar.YEAR] = binding.appliedDatePicker.year
            cal[Calendar.MONTH] = binding.appliedDatePicker.month
            cal[Calendar.DAY_OF_MONTH] = binding.appliedDatePicker.dayOfMonth
            val appliedDate = cal.time

            viewModel.updateJob(
                this.args.jobId,
                binding.jobTitleTextField.editText?.text.toString(),
                binding.jobDescriptionTextField.editText?.text.toString(),
                binding.jobRequirementTextField.editText?.text.toString(),
                appliedDate,
                binding.jobNotesTextField.editText?.text.toString(),
                companyId

            )
            val action = ModifyJobFragmentDirections.actionModifyJobFragmentToJobsAppliedFragment()
            findNavController().navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}