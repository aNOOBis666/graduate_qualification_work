package com.codehunters.glucosereader.module

import com.codehunters.glucose_reader.GlucoseReader
import com.codehunters.glucose_reader.IGlucoseReader
import com.codehunters.glucosereader.ui.navigation.INavigation
import com.codehunters.glucosereader.ui.navigation.Navigation
import com.codehunters.glucosereader.ui.navigation.NavigationDispatcher
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

    @Provides
    @Singleton
    fun providesNavigation(navigationDispatcher: NavigationDispatcher): INavigation = Navigation(navigationDispatcher)

    @Singleton
    @Provides
    fun provideNavigationDispatcher() = NavigationDispatcher()
}