package com.example.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.NavigationTab
import com.example.ui.components.TrickBottomNav
import com.example.ui.components.TrickTopAppBar
import com.example.ui.screens.BioScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.NameGeneratorScreen
import com.example.ui.theme.TrickBackground
import com.example.ui.viewmodel.MainViewModel

@Composable
fun TrickMasterApp(
    viewModel: MainViewModel = viewModel(factory = MainViewModel.Factory(
        androidx.compose.ui.platform.LocalContext.current.applicationContext as android.app.Application
    ))
) {
    var currentTab by rememberSaveable { mutableStateOf(NavigationTab.HOME) }

    Scaffold(
        topBar = {
            TrickTopAppBar(
                title = "Trick Master",
                subtitle = when (currentTab) {
                    NavigationTab.HOME -> null
                    NavigationTab.NAME -> "Names"
                    NavigationTab.BIO -> "Bios"
                    NavigationTab.FAVORITES -> "Favorites"
                }
            )
        },
        bottomBar = {
            TrickBottomNav(
                selectedTab = currentTab,
                onTabSelected = { currentTab = it }
            )
        },
        containerColor = TrickBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(TrickBackground)
        ) {
            Crossfade(
                targetState = currentTab,
                animationSpec = tween(durationMillis = 200),
                label = "tab_crossfade"
            ) { tab ->
                when (tab) {
                    NavigationTab.HOME -> HomeScreen(
                        onNavigateToTab = { currentTab = it }
                    )
                    NavigationTab.NAME -> NameGeneratorScreen(
                        viewModel = viewModel
                    )
                    NavigationTab.BIO -> BioScreen(
                        viewModel = viewModel
                    )
                    NavigationTab.FAVORITES -> FavoritesScreen(
                        viewModel = viewModel,
                        onNavigateToTab = { currentTab = it }
                    )
                }
            }
        }
    }
}
