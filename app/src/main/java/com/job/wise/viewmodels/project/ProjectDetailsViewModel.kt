package com.job.wise.viewmodels.project

import androidx.lifecycle.*
import com.job.wise.data.models.Project
import com.job.wise.data.project.ProjectDao

class ProjectDetailsViewModel(private val projectDao: ProjectDao) : ViewModel() {


     fun retrieveProject(id: Int): LiveData<Project> {
          return projectDao.getProject(id).asLiveData()
     }

}
