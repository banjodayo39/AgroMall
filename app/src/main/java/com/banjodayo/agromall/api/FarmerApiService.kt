package com.banjodayo.agromall.api

import com.banjodayo.agromall.data.FarmerList
import com.banjodayo.agromall.utils.ApiResponse
import org.koin.core.KoinComponent
import retrofit2.http.GET

interface FarmerApiService  {

    @GET("v2/get-sample-farmers")
    suspend fun getFarmerList(): ApiResponse<FarmerList>
}