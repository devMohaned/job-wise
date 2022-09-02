package com.job.wise.data.job

import androidx.room.*
import com.job.wise.data.models.Company
import com.job.wise.data.models.Job
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(job: Job)

    @Update
    suspend fun update(job: Job)

    @Delete
    suspend fun delete(job: Job)


    @Query(
        "SELECT * FROM jobs WHERE id = :id"
    )
    fun getJob(id: Int): Flow<Job>

    @Query(
        "SELECT * FROM companies WHERE id = :id"
    )
    fun getCompany(id: Int): Flow<Company>


    @Query(
        "SELECT * FROM jobs ORDER BY appliedDate DESC"
    )
    fun getJobs(): Flow<List<Job>>

    @MapInfo(valueColumn = "company_name")
    @Query(
        "SELECT j.*, c.name AS company_name FROM jobs AS j LEFT JOIN companies AS c ON c.id = j.company_id ORDER BY appliedDate"
    )
    fun loadJobsWithCompanies(): Flow<Map<Job, String>>

    @Query("SELECT * FROM companies")
    fun getCompanies() : Flow<List<Company>>


}