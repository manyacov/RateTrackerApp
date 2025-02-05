package com.manyacov.data.di

import android.content.Context
import androidx.room.Room
import com.manyacov.data.rate_tracker.datasource.local.RateTrackerDatabase
import com.manyacov.data.rate_tracker.datasource.local.RateTrackerRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRateTrackerDatabase(@ApplicationContext context: Context): RateTrackerDatabase {
        val rateTrackerDatabase = Room.databaseBuilder(
            context,
            RateTrackerRoomDatabase::class.java,
            "rate_tracker_db"
        ).build()
        return RateTrackerDatabase(rateTrackerDatabase)
    }

    @Provides
    @Singleton
    fun provideDao(db: RateTrackerDatabase) = db.rateTrackerDao
}