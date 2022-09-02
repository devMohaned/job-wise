package com.job.wise.viewmodels.company

import androidx.lifecycle.*
import com.job.wise.data.company.CompanyDao
import com.job.wise.data.models.Company
import com.job.wise.data.models.Project
import kotlinx.coroutines.launch

class CompanyViewModel(private val companyDao: CompanyDao) : ViewModel() {
     val allCompanies : LiveData<Map<Company,Int>> = companyDao.loadCompaniesWithProjectsCount().asLiveData()

     fun retrieveProjectForCompany(companyId: Int): LiveData<List<Project>> {
          return companyDao.getProjectsForCompany(companyId).asLiveData()
     }

     fun retrieveUnassignedProjects(): LiveData<List<Project>> {
          return companyDao.getFreeProjects().asLiveData()
     }

     fun unassignProject(project: Project) {
          viewModelScope.launch {
               companyDao.unassignProject(project.id)
          }
     }
     fun assignProject(project: Project, companyId: Int) {
          viewModelScope.launch {
               companyDao.assignProject(project.id, companyId)
          }
     }

}
