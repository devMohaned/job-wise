package com.job.wise.viewmodels.project

import androidx.lifecycle.*
import com.job.wise.data.models.Project
import com.job.wise.data.project.ProjectDao
import kotlinx.coroutines.launch

class ProjectViewModel(private val projectDao: ProjectDao) : ViewModel() {
     val allProjects : LiveData<List<Project>> = projectDao.getProjects().asLiveData()
     fun deleteProject(project: Project) {
          viewModelScope.launch {
               projectDao.delete(project)
          }
     }

}
