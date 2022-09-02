package com.job.wise.data.company

import androidx.room.*
import com.job.wise.data.models.Company
import com.job.wise.data.models.Project
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(company: Company)

    @Update
    suspend fun update(company: Company)

    @Delete
    suspend fun delete(company: Company)

    @Query("UPDATE jobs SET company_id = -1 WHERE company_id = :id")
    suspend fun deleteJobsIfFound(id: Int)

    @Query(
        "UPDATE projects SET company_id = :companyId WHERE  id = :projectId"
    )
    suspend fun assignProject(projectId: Int, companyId: Int)


    @Query(
        "UPDATE projects SET company_id = -1 WHERE  id = :projectId"
    )
    suspend fun unassignProject(projectId: Int)

    @Query(
        "SELECT * FROM companies WHERE id = :id"
    )
    fun getProject(id: Int): Flow<Company>

    @Query(
        "SELECT * FROM projects WHERE company_id = :id"
    )
    fun getProjectsForCompany(id: Int): Flow<List<Project>>

    @Query(
        "SELECT * FROM projects WHERE company_id = -1"
    )
    fun getFreeProjects(): Flow<List<Project>>


    @MapInfo(valueColumn = "project_count")
    @Query(
        "SELECT c.*, COUNT(p.id) AS project_count FROM companies AS c LEFT JOIN projects AS p ON c.id = p.company_id GROUP BY (c.id)"
    )
    fun loadCompaniesWithProjectsCount(): Flow<Map<Company, Int>>


}

