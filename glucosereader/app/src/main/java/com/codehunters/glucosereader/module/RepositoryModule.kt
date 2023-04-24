package com.codehunters.glucosereader.module

import com.codehunters.local_storage.interfaces.IGlucoseInterface
import com.codehunters.local_storage.interfaces.ISharedPreferencesManager
import com.codehunters.repository.GlucoseRepository
import com.codehunters.repository.NotificationRepository
import com.codehunters.repository.interfaces.IGlucoseRepository
import com.codehunters.repository.interfaces.INotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun providesGlucoseRepository(
        glucoseInterface: IGlucoseInterface
    ): IGlucoseRepository = GlucoseRepository(glucoseInterface)

    @Provides
    @Singleton
    fun providesNotificationRepository(
        sharedPreferencesManager: ISharedPreferencesManager
    ): INotificationRepository = NotificationRepository(sharedPreferencesManager)
}