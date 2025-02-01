package com.manyacov.data.di

import android.content.Context
import com.manyacov.data.BuildConfig
import com.manyacov.data.rate_tracker.datasource.local.RateTrackerDatabase
import com.manyacov.data.rate_tracker.datasource.remote.api.RateTrackerApi
import com.manyacov.data.rate_tracker.datasource.remote.utils.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.RATE_TRACKER_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRateTrackerApi(retrofit: Retrofit): RateTrackerApi {
        return retrofit.create(RateTrackerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRateTrackerDatabase(
        @ApplicationContext context: Context
    ): RateTrackerDatabase {
        return RateTrackerDatabase(context)
    }
}