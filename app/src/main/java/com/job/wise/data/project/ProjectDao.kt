package com.job.wise.data.project

import androidx.room.*
import com.job.wise.data.models.Project
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(project: Project)

    @Update
    suspend fun update(project: Project)

    @Delete
    suspend fun delete(project: Project)

    @Query("SELECT * FROM projects WHERE id = :id")
    fun getProject(id: Int) : Flow<Project>

    @Query("SELECT * FROM projects")
    fun getProjects() : Flow<List<Project>>


}