package com.job.wise.viewmodels.project

import androidx.lifecycle.*
import com.job.wise.data.models.Project
import com.job.wise.data.project.ProjectDao
import kotlinx.coroutines.launch

class ModifyProjectViewModel(private val projectDao: ProjectDao) : ViewModel() {

    private fun updateProject(project: Project) {
        viewModelScope.launch {
            projectDao.update(project)
        }
    }


    fun retrieveProject(id: Int): LiveData<Project> {
        return projectDao.getProject(id).asLiveData()
    }


    private fun insertProject(project: Project) {
        viewModelScope.launch {
            projectDao.insert(project)
        }
    }

    private fun getNewProjectEntry(
        name: String,
        description: String?,
        client: String?
    ): Project {
        return Project(name = name, description = description, madeTo = client)
    }

    fun addNewProject(projectName: String, projectDescription: String, projectClient: String) {
        val newProject = getNewProjectEntry(projectName, projectDescription, projectClient)
        insertProject(newProject)
    }

    fun isEntryValid(projectName: String): Boolean {
        if (projectName.isBlank()) {
            return false
        }
        return true
    }

    private fun getUpdatedProjectEntry(
        projectId: Int,
        projectName: String, projectDescription: String, projectClient: String
    ): Project {
        return Project(
            id = projectId,
            name = projectName,
            description = projectDescription,
            madeTo = projectClient
        )
    }

    fun updateProject(
        projectId: Int,
        projectName: String, projectDescription: String, projectClient: String
    ) {
        val updatedItem =
            getUpdatedProjectEntry(projectId, projectName, projectDescription, projectClient)
        updateProject(updatedItem)
    }

}
