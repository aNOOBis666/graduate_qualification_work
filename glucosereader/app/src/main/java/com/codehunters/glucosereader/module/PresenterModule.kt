package com.codehunters.glucosereader.module

import com.codehunters.presenter.GlucosePresenter
import com.codehunters.presenter.NotificationPresenter
import com.codehunters.presenter.interfaces.IGlucosePresenter
import com.codehunters.presenter.interfaces.INotificationPresenter
import com.codehunters.repository.interfaces.IGlucoseRepository
import com.codehunters.repository.interfaces.INotificationRepository
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
    fun providesGlucosePresenter(
        glucoseRepository: IGlucoseRepository
    ): IGlucosePresenter = GlucosePresenter(glucoseRepository)

    @Provides
    @Singleton
    fun providesNotificationPresenter(
        notificationRepository: INotificationRepository
    ): INotificationPresenter = NotificationPresenter(notificationRepository)
}