package com.job.wise.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
/*
interface JobWiseService{
@GET("api/project")
suspend fun getProjects(): NetworkProjectContainer

}


object JobWiseNetwork {

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://localhost:7131/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val jobWise: JobWiseService = retrofit.create(JobWiseService::class.java)

}*/