/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("UnusedVariable", "unused")

package com.classictoon.novel.ui.main

import android.annotation.SuppressLint
import android.database.CursorWindow
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.togetherWith
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import com.classictoon.novel.R
import com.classictoon.novel.domain.navigator.NavigatorItem
import com.classictoon.novel.domain.navigator.StackEvent
import com.classictoon.novel.domain.ui.isDark
import com.classictoon.novel.domain.ui.isPureDark
import com.classictoon.novel.presentation.core.components.navigation_bar.NavigationBar
import com.classictoon.novel.presentation.core.components.navigation_rail.NavigationRail
import com.classictoon.novel.presentation.main.MainActivityKeyboardManager
import com.classictoon.novel.presentation.navigator.Navigator
import com.classictoon.novel.presentation.navigator.NavigatorTabs
import com.classictoon.novel.presentation.server_books.ServerBooksScreen
import com.classictoon.novel.ui.browse.BrowseModel
import com.classictoon.novel.ui.browse.BrowseScreen
import com.classictoon.novel.ui.history.HistoryModel
import com.classictoon.novel.ui.history.HistoryScreen
import com.classictoon.novel.ui.library.LibraryModel
import com.classictoon.novel.ui.library.LibraryScreen
import com.classictoon.novel.ui.settings.SettingsModel
import com.classictoon.novel.ui.start.StartScreen
import com.classictoon.novel.ui.theme.BookStoryTheme
import com.classictoon.novel.ui.theme.Transitions
import com.classictoon.novel.ui.splash.SplashScreen
import java.lang.reflect.Field


@SuppressLint("DiscouragedPrivateApi")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // Creating an instance of Models
    private val mainModel: MainModel by viewModels()
    private val settingsModel: SettingsModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Splash screen
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                /*!mainModel.isReady.value*/ false
            }
        }

        // Default super
        super.onCreate(savedInstanceState)

        // Bigger Cursor size for Room
        try {
            val field: Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.isAccessible = true
            field.set(null, 100 * 1024 * 1024)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Initializing the MainModel
        mainModel.init(settingsModel.isReady)

        // Edge to edge
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // Initializing Screen Models
            val libraryModel = hiltViewModel<LibraryModel>()
            val historyModel = hiltViewModel<HistoryModel>()
            val browseModel = hiltViewModel<BrowseModel>()

            val state = mainModel.state.collectAsStateWithLifecycle()
            val isLoaded = mainModel.isReady.collectAsStateWithLifecycle()

            val tabs = listOf(
                NavigatorItem(
                    screen = ServerBooksScreen,
                    title = R.string.home_screen,
                    tooltip = R.string.home_content_desc,
                    selectedIcon = R.drawable.home_screen_filled,
                    unselectedIcon = R.drawable.home_screen_outlined
                ),
                NavigatorItem(
                    screen = HistoryScreen,
                    title = R.string.explore_screen,
                    tooltip = R.string.explore_content_desc,
                    selectedIcon = R.drawable.explore_screen_filled,
                    unselectedIcon = R.drawable.explore_screen_outlined
                ),
                NavigatorItem(
                    screen = BrowseScreen,
                    title = R.string.mine_screen,
                    tooltip = R.string.mine_content_desc,
                    selectedIcon = R.drawable.mine_screen_filled,
                    unselectedIcon = R.drawable.mine_screen_outlined
                )
            )

            MainActivityKeyboardManager()

            BookStoryTheme(
                theme = state.value.theme,
                isDark = state.value.darkTheme.isDark(),
                isPureDark = state.value.pureDark.isPureDark(this),
                themeContrast = state.value.themeContrast
            ) {
                Navigator(
                    initialScreen = SplashScreen,
                    transitionSpec = { lastEvent ->
                        when (lastEvent) {
                            StackEvent.Default -> {
                                Transitions.SlidingTransitionIn
                                    .togetherWith(Transitions.SlidingTransitionOut)
                            }

                            StackEvent.Pop -> {
                                Transitions.BackSlidingTransitionIn
                                    .togetherWith(Transitions.BackSlidingTransitionOut)
                            }
                        }
                    },
                    contentKey = {
                        when (it) {
                            LibraryScreen, HistoryScreen, BrowseScreen -> "tabs"
                            else -> it
                        }
                    },
                    backHandlerEnabled = { it != StartScreen && it != SplashScreen }
                ) { screen ->
                    when (screen) {
                        LibraryScreen, HistoryScreen, BrowseScreen -> {
                            NavigatorTabs(
                                currentTab = screen,
                                transitionSpec = {
                                    Transitions.FadeTransitionIn
                                        .togetherWith(Transitions.FadeTransitionOut)
                                },
                                navigationBar = { NavigationBar(tabs = tabs) },
                                navigationRail = { NavigationRail(tabs = tabs) }
                            ) { tab ->
                                tab.Content()
                            }
                        }

                        else -> {
                            screen.Content()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        cacheDir.deleteRecursively()
        super.onDestroy()
    }
}
