package com.codehunters.glucosereader.module

import com.codehunters.local_storage.interfaces.IGlucoseInterface
import com.codehunters.repository.GlucoseRepository
import com.codehunters.repository.interfaces.IGlucoseRepository
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
    fun provideGlucoseRepository(
        glucoseInterface: IGlucoseInterface
    ): IGlucoseRepository = GlucoseRepository(glucoseInterface)
}