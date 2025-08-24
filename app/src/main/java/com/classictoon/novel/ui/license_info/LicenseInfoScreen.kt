/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.license_info

import android.os.Parcelable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.util.withJson
import kotlinx.parcelize.Parcelize
import com.classictoon.novel.R
import com.classictoon.novel.domain.navigator.Screen
import com.classictoon.novel.presentation.core.components.top_bar.collapsibleTopAppBarScrollBehavior
import com.classictoon.novel.presentation.license_info.LicenseInfoContent
import com.classictoon.novel.presentation.navigator.LocalNavigator
import com.classictoon.novel.ui.about.AboutModel

@Parcelize
data class LicenseInfoScreen(val uniqueId: String) : Screen, Parcelable {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel = hiltViewModel<AboutModel>()

        val context = LocalContext.current
        val (scrollBehavior, listState) = TopAppBarDefaults.collapsibleTopAppBarScrollBehavior()
        val library = remember {
            derivedStateOf {
                Libs.Builder().withJson(context, R.raw.aboutlibraries).build()
                    .libraries.find { it.uniqueId == uniqueId }
            }
        }

        LicenseInfoContent(
            library = library.value,
            scrollBehavior = scrollBehavior,
            listState = listState,
            navigateToBrowserPage = screenModel::onEvent,
            navigateBack = {
                navigator.pop()
            }
        )
    }
}
