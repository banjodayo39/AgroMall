package com.banjodayo.agromall.di

import com.banjodayo.agromall.api.FarmerApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule  = module {
    single { provideFarmerApi(get()) }
}

fun provideFarmerApi(retrofit: Retrofit): FarmerApiService{
    return retrofit.create(FarmerApiService::class.java)
}