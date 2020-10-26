package com.banjodayo.agromall.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.banjodayo.agromall.data.FarmerDAO
import com.banjodayo.agromall.data.FarmerDatabase
import org.koin.dsl.module

var databaseModule = module {
    single { provideFarmerDatabase(get()) }
    single { provideFarmerDao(get()) }
}

fun provideFarmerDatabase(context: Application) : FarmerDatabase{
    return Room.databaseBuilder(context, FarmerDatabase::class.java, "farmer_db")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideFarmerDao(database: FarmerDatabase): FarmerDAO = database.farmerDao()