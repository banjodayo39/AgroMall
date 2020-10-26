package com.banjodayo.agromall.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Farmer::class],
    version = 1,
    exportSchema = false
)


abstract class FarmerDatabase : RoomDatabase() {
    abstract fun farmerDao() : FarmerDAO
}