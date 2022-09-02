package com.job.wise.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.job.wise.data.job.JobDao
import com.job.wise.viewmodels.job.JobDetailsViewModel
import com.job.wise.viewmodels.job.JobViewModel
import com.job.wise.viewmodels.job.ModifyJobViewModel


class JobViewModelFactory(private val jobDao: JobDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if(modelClass.isAssignableFrom(JobViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return JobViewModel(jobDao) as T
        }else   if(modelClass.isAssignableFrom(JobDetailsViewModel::class.java))
    {
        @Suppress("UNCHECKED_CAST")
        return JobDetailsViewModel(jobDao) as T
    }
    else   if(modelClass.isAssignableFrom(ModifyJobViewModel::class.java))
    {
        @Suppress("UNCHECKED_CAST")
        return ModifyJobViewModel(jobDao) as T
    }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}