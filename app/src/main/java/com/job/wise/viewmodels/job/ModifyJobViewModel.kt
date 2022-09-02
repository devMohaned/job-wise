package com.job.wise.viewmodels.job

import androidx.lifecycle.*
import com.job.wise.data.job.JobDao
import com.job.wise.data.models.Company
import com.job.wise.data.models.Job
import kotlinx.coroutines.launch
import java.util.*

class ModifyJobViewModel(private val jobDao: JobDao) : ViewModel() {
    val availableCompanies: LiveData<List<Company>> = jobDao.getCompanies().asLiveData()

    private fun updateJob(job: Job) {
        viewModelScope.launch {
            jobDao.update(job)
        }
    }

    fun deleteJob(job: Job) {
        viewModelScope.launch {
            jobDao.delete(job)
        }
    }


    fun retrieveJob(id: Int): LiveData<Job> {
        return jobDao.getJob(id).asLiveData()
    }


    private fun insertJob(job: Job) {
        viewModelScope.launch {
            jobDao.insert(job)
        }
    }

    private fun getNewJobEntry(
        title: String,
        description: String?,
        requirements: String?,
        appliedDate: Date?,
        notes: String?,
        companyAppliedId: Int
    ): Job {
        return Job(
            title = title,
            description = description,
            requirements = requirements,
            appliedDate = appliedDate,
            notes = notes,
            companyAppliedId = companyAppliedId
        )
    }

    fun addNewJob(
        title: String,
        description: String?,
        requirements: String?,
        appliedDate: Date?,
        notes: String?,
        companyAppliedId: Int
    ) {
        val newJob = getNewJobEntry(
            title,
            description,
            requirements,
            appliedDate,
            notes,
            companyAppliedId
        )

        insertJob(newJob)
    }

    fun isEntryValid(
        title: String
    ): Boolean {
        if (title.isBlank()) {
            return false
        }
        return true
    }

    private fun getUpdatedCompanyEntry(
        jobId: Int,
        title: String,
        description: String?,
        requirements: String?,
        appliedDate: Date?,
        notes: String?,
        companyAppliedId: Int
    ): Job {
        return Job(
            id = jobId,
            title = title,
            description = description,
            requirements = requirements,
            appliedDate = appliedDate,
            notes = notes,
            companyAppliedId = companyAppliedId,
        )
    }

    fun updateJob(
        jobId: Int,
        title: String,
        description: String?,
        requirements: String?,
        appliedDate: Date?,
        notes: String?,
        companyAppliedId: Int
    ) {
        val updatedItem =
            getUpdatedCompanyEntry(
                jobId,
                title,
                description,
                requirements,
                appliedDate,
                notes,
                companyAppliedId
            )
        updateJob(updatedItem)
    }



}
