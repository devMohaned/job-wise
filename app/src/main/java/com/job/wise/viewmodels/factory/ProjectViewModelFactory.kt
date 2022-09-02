package com.job.wise.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.job.wise.data.project.ProjectDao
import com.job.wise.viewmodels.project.ModifyProjectViewModel
import com.job.wise.viewmodels.project.ProjectDetailsViewModel
import com.job.wise.viewmodels.project.ProjectViewModel


class ProjectViewModelFactory(private val projectDao: ProjectDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ModifyProjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ModifyProjectViewModel(projectDao) as T
        }else if(modelClass.isAssignableFrom(ProjectViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return ProjectViewModel(projectDao) as T
        }
        else if(modelClass.isAssignableFrom(ProjectDetailsViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return ProjectDetailsViewModel(projectDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}