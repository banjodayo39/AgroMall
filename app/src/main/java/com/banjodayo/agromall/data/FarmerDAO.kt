package com.banjodayo.agromall.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface  FarmerDAO {

    @Query("SELECT * FROM farmers")
    fun farmerDataSource(): DataSource.Factory<Int, Farmer>

    @Insert
    fun addFarmer(farmer: Farmer)
}