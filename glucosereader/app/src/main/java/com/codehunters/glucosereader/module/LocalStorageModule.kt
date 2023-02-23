package com.codehunters.glucosereader.module

import android.content.Context
import androidx.room.Room
import com.codehunters.local_storage.GlucoseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalStorageModule {

    @Singleton
    @Provides
    fun providesFavoritesDatabase(
        @ApplicationContext context: Context
    ): GlucoseDatabase {
        return Room
            .databaseBuilder(
                context,
                GlucoseDatabase::class.java,
                "glucose"
            )
            .fallbackToDestructiveMigration()
            .build()
    }
}