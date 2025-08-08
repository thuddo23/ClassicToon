/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.about

import android.os.Parcelable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.parcelize.Parcelize
import com.classictoon.novel.domain.navigator.Screen
import com.classictoon.novel.presentation.about.AboutContent
import com.classictoon.novel.presentation.core.components.top_bar.collapsibleTopAppBarScrollBehavior
import com.classictoon.novel.presentation.navigator.LocalNavigator
import com.classictoon.novel.ui.credits.CreditsScreen
import com.classictoon.novel.ui.licenses.LicensesScreen

@Parcelize
object AboutScreen : Screen, Parcelable {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel = hiltViewModel<AboutModel>()

        val (scrollBehavior, listState) = TopAppBarDefaults.collapsibleTopAppBarScrollBehavior()

        AboutContent(
            scrollBehavior = scrollBehavior,
            listState = listState,
            navigateToBrowserPage = screenModel::onEvent,
            navigateToLicenses = {
                navigator.push(LicensesScreen)
            },
            navigateToCredits = {
                navigator.push(CreditsScreen)
            },
            navigateBack = {
                navigator.pop()
            }
        )
    }
}
