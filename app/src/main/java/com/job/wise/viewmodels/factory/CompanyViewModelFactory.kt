package com.job.wise.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.job.wise.data.company.CompanyDao
import com.job.wise.viewmodels.company.CompanyDetailsViewModel
import com.job.wise.viewmodels.company.CompanyViewModel
import com.job.wise.viewmodels.company.ModifyCompanyViewModel


class CompanyViewModelFactory(private val companyDao: CompanyDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if(modelClass.isAssignableFrom(CompanyViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return CompanyViewModel(companyDao) as T
        }else   if(modelClass.isAssignableFrom(CompanyDetailsViewModel::class.java))
    {
        @Suppress("UNCHECKED_CAST")
        return CompanyDetailsViewModel(companyDao) as T
    }
    else   if(modelClass.isAssignableFrom(ModifyCompanyViewModel::class.java))
    {
        @Suppress("UNCHECKED_CAST")
        return ModifyCompanyViewModel(companyDao) as T
    }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}