package com.banjodayo.agromall.di

import com.banjodayo.agromall.viewmodel.FarmerViewModel
import com.banjodayo.agromall.viewmodel.LoginViewModel
import org.koin.dsl.module

var viewModelModule = module {
    factory { LoginViewModel() }
    factory { FarmerViewModel() }
}