package com.banjodayo.agromall.di

import com.banjodayo.agromall.data.FarmerRepository
import org.koin.dsl.module

var repositoryModule = module {
    factory { FarmerRepository() }
}