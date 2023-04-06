package com.codehunters.glucosereader.module

import com.codehunters.glucose_reader.GlucoseReader
import com.codehunters.glucose_reader.IGlucoseReader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideGlucoseReader(): IGlucoseReader = GlucoseReader()

}