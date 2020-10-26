package com.banjodayo.agromall

import android.app.Application
import com.banjodayo.agromall.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AgroApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        _instance = this

        startKoin {
            androidLogger()
            androidContext(this@AgroApplication)

            modules(listOf(
                networkModule,
                apiModule,
                databaseModule,
                repositoryModule,
                viewModelModule
            ))
        }
    }

    companion object {

        private var _instance: AgroApplication? = null

        /** Change this to `false` when you want to use the downloadable Emoji font.  */
        private val USE_BUNDLED_EMOJI = false

        val app: Application?
            get() = _instance
    }
}