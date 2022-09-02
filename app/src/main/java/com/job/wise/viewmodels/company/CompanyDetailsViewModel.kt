package com.job.wise.viewmodels.company

import androidx.lifecycle.*
import com.job.wise.data.company.CompanyDao
import com.job.wise.data.models.Company
import com.job.wise.data.models.Project

class CompanyDetailsViewModel(private val companyDao: CompanyDao) : ViewModel() {
     fun retrieveCompany(id: Int): LiveData<Company> {
          return companyDao.getProject(id).asLiveData()
     }
     fun retrieveCompanyProjects(id: Int): LiveData<List<Project>> {
          return companyDao.getProjectsForCompany(id).asLiveData()
     }
}
