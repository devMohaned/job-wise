package com.job.wise.viewmodels.company

import androidx.lifecycle.*
import com.job.wise.data.company.CompanyDao
import com.job.wise.data.models.Company
import com.job.wise.data.models.Project
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ModifyCompanyViewModel(private val companyDao: CompanyDao) : ViewModel() {

    private fun updateCompany(company: Company) {
        viewModelScope.launch {
            companyDao.update(company)
        }
    }
    fun  deleteCompany(company: Company)  {
        runBlocking { // We need to queue the following statements per row.
            companyDao.deleteJobsIfFound(company.id) // Delete Relations
            companyDao.delete(company) // Delete the company
        }

    }


    fun retrieveCompany(id: Int): LiveData<Company> {
        return companyDao.getProject(id).asLiveData()
    }


    private fun insertCompany(company: Company) {
        viewModelScope.launch {
            companyDao.insert(company)
        }
    }

    private fun getNewCompanyEntry(
        name: String,
        rating: Float?,
    ): Company {
        return Company(name = name, rating = rating)
    }

    fun addNewCompany(companyName: String, companyRating: Float?) {
        val newCompany = getNewCompanyEntry(companyName,companyRating)
        insertCompany(newCompany)
    }

    fun isEntryValid(companyName: String, companyRating: Float?): Boolean {
        if (companyName.isBlank() || companyRating == null){
            return false
        }
        return true
    }

    private fun getUpdatedCompanyEntry(
        companyId: Int,
        companyName: String, companyRating: Float?
    ): Company {
        return Company(
            id = companyId,
            name = companyName,
            rating = companyRating
        )
    }

    fun updateCompany(
        companyId: Int,
        companyName: String, companyRating: Float?
    ) {
        val updatedItem =
            getUpdatedCompanyEntry(companyId, companyName, companyRating)
        updateCompany(updatedItem)
    }


}
