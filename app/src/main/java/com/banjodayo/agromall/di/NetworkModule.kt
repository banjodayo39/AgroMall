package com.banjodayo.agromall.di

import android.util.Log
import com.banjodayo.agromall.BuildConfig
import com.banjodayo.agromall.utils.ResponseHandler
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

var networkModule = module{
   single { ResponseHandler }
   single { provideGson() }
   single { provideHttpInterceptorLogger() }
   single { provideHttpLoggingInterceptor(get()) }
   single { provideOkhttpClient(get()) }
   single { provideRetrofit(get(), get()) }
}

val HTTP_LOG_TAG = "http"

fun provideGson(): Gson {
   val gsonBuilder = GsonBuilder()
   gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
   return gsonBuilder.create()
}

fun provideHttpInterceptorLogger(): HttpLoggingInterceptor.Logger {
   return HttpLoggingInterceptor.Logger { message -> Log.d(HTTP_LOG_TAG, message) }
}

fun provideHttpLoggingInterceptor(logger: HttpLoggingInterceptor.Logger): HttpLoggingInterceptor {
   val interceptor = HttpLoggingInterceptor(logger)
   interceptor.level = HttpLoggingInterceptor.Level.BODY
   interceptor.redactHeader("Authorization")
   interceptor.redactHeader("Cookie")
   interceptor.redactHeader("X-Auth-Token")
   return interceptor
}

fun provideOkhttpClient(
   loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {

   val httpClient = OkHttpClient.Builder()
   httpClient.addInterceptor(loggingInterceptor)
   httpClient.connectTimeout(7, TimeUnit.SECONDS)
   httpClient.readTimeout(7, TimeUnit.SECONDS)
   return httpClient.build()
}

fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
   return Retrofit.Builder()
      .baseUrl(BuildConfig.API_URL)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .client(okHttpClient)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()
}