package com.job.wise.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "companies")
class Company(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "rating")
    var rating: Float?,
   /* private var jobs: List<Job>,
    private var projects: List<Project>*/
)
