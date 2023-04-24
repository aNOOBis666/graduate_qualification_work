package com.codehunters.glucosereader.ui.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

@ActivityRetainedScoped
class NavigationDispatcher {
    private val navigationCommandMutableSharedFlow = MutableSharedFlow<NavigationCommand>()
    val navigationCommandFlow: Flow<NavigationCommand> by ::navigationCommandMutableSharedFlow

    suspend fun navigate(
        @IdRes destinationId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null
    ) {
        emit { navController ->
            navController.navigate(destinationId, args, navOptions, navigatorExtras)
        }
    }

    private suspend fun emit(command: NavigationCommand) {
        navigationCommandMutableSharedFlow.emit(command)
    }
}