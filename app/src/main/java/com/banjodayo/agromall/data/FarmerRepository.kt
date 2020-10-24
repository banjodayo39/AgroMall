package com.banjodayo.agromall.data

import com.banjodayo.agromall.api.FarmerApiService
import com.banjodayo.agromall.utils.ApiResponse
import com.banjodayo.agromall.utils.Resource
import com.banjodayo.agromall.utils.ResponseHandler
import org.koin.core.KoinComponent
import org.koin.core.inject

class FarmerRepository : KoinComponent{

    private val api : FarmerApiService by inject()
    private val responseHandler : ResponseHandler by inject()
    private val db : FarmerDAO by inject()

    suspend fun getFarmerData(): Resource<ApiResponse<FarmerList>>{
        return  try {
            val data = api.getFarmerList()
            if(data != null) {
                data.data?.farmers?.forEach {
                    db.addFarmer(it)
                }
            }
           responseHandler.handleSuccess(data)

        } catch (e: Exception){
            responseHandler.handleException(e)
        }
    }
}