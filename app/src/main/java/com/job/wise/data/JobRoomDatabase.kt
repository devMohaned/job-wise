package com.job.wise.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.job.wise.data.company.CompanyDao
import com.job.wise.data.job.JobDao
import com.job.wise.data.models.Company
import com.job.wise.data.models.Job
import com.job.wise.data.models.Project
import com.job.wise.data.project.ProjectDao
import com.job.wise.util.DateConverter

@Database(entities = [Project::class, Company::class, Job::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class JobRoomDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun companyDao(): CompanyDao
    abstract fun jobDao(): JobDao

    companion object {
        @Volatile // The value of a volatile variable will never be cached, and all writes and reads will be done to and from the main memory. This helps make sure the value of INSTANCE is always up-to-date and the same for all execution threads. It means that changes made by one thread to INSTANCE are visible to all other threads immediately.
        private var _INSTANCE: JobRoomDatabase? = null

        fun getDatabase(context: Context): JobRoomDatabase {
            return _INSTANCE
                ?: synchronized(this) { // Multiple threads can potentially run into a race condition and ask for a database instance at the same time, resulting in two databases instead of one. Wrapping the code to get the database inside a synchronized block means that only one thread of execution at a time can enter this block of code, which makes sure the database only gets initialized once.
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        JobRoomDatabase::class.java,
                        "job_wise_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    _INSTANCE = instance
                    return instance
                }
        }

    }

}