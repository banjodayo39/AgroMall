package com.banjodayo.agromall.viewmodel

import androidx.lifecycle.*
import androidx.paging.toLiveData
import com.banjodayo.agromall.data.Farmer
import com.banjodayo.agromall.data.FarmerDAO
import com.banjodayo.agromall.data.FarmerList
import com.banjodayo.agromall.data.FarmerRepository
import com.banjodayo.agromall.utils.ApiResponse
import com.banjodayo.agromall.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class FarmerViewModel : ViewModel(), KoinComponent {

    private val repo : FarmerRepository by inject()
    private val farmerDb : FarmerDAO by inject()
    private var _farmerLiveData = MutableLiveData<Resource<ApiResponse<FarmerList>>>()

    val farmerLiveData
       get() = Transformations.map(farmerDb.farmerDataSource().toLiveData(pageSize = 50)){it}

    fun getFarmerData() :LiveData<Resource<ApiResponse<FarmerList>>> {
        viewModelScope.launch(Dispatchers.IO){
            _farmerLiveData.postValue(repo.getFarmerData())
        }
        return  _farmerLiveData
    }

    fun saveDb(farmer: Farmer){
        farmerDb.addFarmer(farmer)
    }

}