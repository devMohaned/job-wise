package com.job.wise.data

import android.app.Application

class JobWiseApplication : Application() {
    val database: JobRoomDatabase by lazy { JobRoomDatabase.getDatabase(this) }

}