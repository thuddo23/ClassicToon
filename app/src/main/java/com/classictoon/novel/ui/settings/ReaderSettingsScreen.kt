/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.settings

import android.os.Parcelable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import com.classictoon.novel.domain.navigator.Screen
import com.classictoon.novel.presentation.core.components.top_bar.collapsibleTopAppBarScrollBehavior
import com.classictoon.novel.presentation.navigator.LocalNavigator
import com.classictoon.novel.presentation.settings.reader.ReaderSettingsContent

@Parcelize
object ReaderSettingsScreen : Screen, Parcelable {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val (scrollBehavior, listState) = TopAppBarDefaults.collapsibleTopAppBarScrollBehavior()

        ReaderSettingsContent(
            scrollBehavior = scrollBehavior,
            listState = listState,
            navigateBack = {
                navigator.pop()
            }
        )
    }
}
