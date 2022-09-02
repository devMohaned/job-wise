package com.job.wise.viewmodels.job

import androidx.lifecycle.*
import com.job.wise.data.job.JobDao
import com.job.wise.data.models.Company
import com.job.wise.data.models.Job
import com.job.wise.data.models.Project

class JobDetailsViewModel(private val jobDao: JobDao) : ViewModel() {
     fun retrieveJob(id: Int): LiveData<Job> {
          return jobDao.getJob(id).asLiveData()
     }
     fun retrieveCompany(id: Int): LiveData<Company> {
          return jobDao.getCompany(id).asLiveData()
     }

     fun retrieveJobWithCompany(id: Int): LiveData<Job> {
          return jobDao.getJob(id).asLiveData()
     }
}
