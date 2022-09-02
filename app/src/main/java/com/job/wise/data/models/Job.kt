package com.job.wise.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.job.wise.data.models.Company
import java.util.*

@Entity(tableName = "jobs")
class Job(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String?,
    @ColumnInfo(name = "requirements")
    var requirements: String?,
    @ColumnInfo(name = "appliedDate")
    var appliedDate: Date?,
    @ColumnInfo(name = "notes")
    var notes: String?,
    @ColumnInfo(name = "company_id")
    var companyAppliedId: Int
)