package com.manyacov.ratetrackerapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {

//    val loggingInterceptor = HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY
//    }
//
//    val client = OkHttpClient.Builder()
//        .addInterceptor(loggingInterceptor)
//        .build()





//    class HeaderInterceptor : Interceptor {
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val originalRequest = chain.request()
//            val requestWithHeaders = originalRequest.newBuilder()
//                .addHeader("apikey", BuildConfig.RATE_TRACKER_API_KEY)
//                .build()
//            return chain.proceed(requestWithHeaders)
//        }
//    }
//
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(HeaderInterceptor())
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BuildConfig.RATE_TRACKER_API_BASE_URL)
//            .client(okHttpClient)
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()
//    }



//    @Provides
//    @Singleton
//    fun provideExchangeRatesApi(retrofit: Retrofit): RateTrackerApi {
//        return retrofit.create(RateTrackerApi::class.java)
//    }
//}