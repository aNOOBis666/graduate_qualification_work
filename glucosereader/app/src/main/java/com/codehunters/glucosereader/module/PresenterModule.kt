package com.codehunters.glucosereader.module

import com.codehunters.presenter.GlucosePresenter
import com.codehunters.presenter.interfaces.ITransceiveObject
import com.codehunters.presenter.TransceiveObject
import com.codehunters.presenter.interfaces.IGlucosePresenter
import com.codehunters.repository.interfaces.IGlucoseRepository
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

    @Provides
    @Singleton
    fun provideGlucosePresenter(
        glucoseRepository: IGlucoseRepository
    ): IGlucosePresenter = GlucosePresenter(glucoseRepository)
}