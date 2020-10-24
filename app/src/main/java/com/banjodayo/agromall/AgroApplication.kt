package com.banjodayo.agromall

import android.app.Application
import com.banjodayo.agromall.di.viewModelModule
import org.koin.core.context.startKoin

class AgroApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(
                viewModelModule
            ))
        }
    }
}