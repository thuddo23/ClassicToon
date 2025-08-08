/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.help

import android.os.Parcelable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.parcelize.Parcelize
import com.classictoon.novel.domain.navigator.Screen
import com.classictoon.novel.presentation.core.components.top_bar.collapsibleTopAppBarScrollBehavior
import com.classictoon.novel.presentation.help.HelpContent
import com.classictoon.novel.presentation.navigator.LocalNavigator
import com.classictoon.novel.ui.browse.BrowseScreen
import com.classictoon.novel.ui.main.MainEvent
import com.classictoon.novel.ui.main.MainModel
import com.classictoon.novel.ui.start.StartScreen

@Parcelize
data class HelpScreen(val fromStart: Boolean) : Screen, Parcelable {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val mainModel = hiltViewModel<MainModel>()

        val (scrollBehavior, listState) = TopAppBarDefaults.collapsibleTopAppBarScrollBehavior()

        HelpContent(
            fromStart = fromStart,
            scrollBehavior = scrollBehavior,
            listState = listState,
            changeShowStartScreen = mainModel::onEvent,
            navigateToBrowse = {
                navigator.push(BrowseScreen, saveInBackStack = false)
            },
            navigateToStart = {
                mainModel.onEvent(MainEvent.OnChangeShowStartScreen(true))
                navigator.push(StartScreen, saveInBackStack = false)
            },
            navigateBack = {
                navigator.pop()
            }
        )
    }
}
