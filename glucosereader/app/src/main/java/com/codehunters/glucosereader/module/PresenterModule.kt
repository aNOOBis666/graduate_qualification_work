package com.codehunters.glucosereader.module

import com.codehunters.presenter.ITransceiveObject
import com.codehunters.presenter.TransceiveObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PresenterModule {

    @Provides
    @Singleton
    fun provideTransceiveObject(): ITransceiveObject = TransceiveObject()
}