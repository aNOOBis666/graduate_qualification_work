package com.codehunters.glucosereader.ui.navigation

import com.codehunters.glucosereader.R

class Navigation(
    private val navigationDispatcher: NavigationDispatcher
): INavigation {

    override suspend fun showNotificationDialog() {
        navigationDispatcher.navigate(R.id.notification_destination)
    }
}