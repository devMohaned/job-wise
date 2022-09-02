package com.job.wise.viewmodels.job

import androidx.lifecycle.*
import com.job.wise.data.company.CompanyDao
import com.job.wise.data.job.JobDao
import com.job.wise.data.models.Company
import com.job.wise.data.models.Job
import com.job.wise.data.models.Project
import kotlinx.coroutines.launch

class JobViewModel(private val jobDao: JobDao) : ViewModel() {
     val allJobs : LiveData<Map<Job, String>> = jobDao.loadJobsWithCompanies().asLiveData()

}
