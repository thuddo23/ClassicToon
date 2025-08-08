/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.settings.browse.display.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.classictoon.novel.R
import com.classictoon.novel.domain.browse.display.BrowseLayout
import com.classictoon.novel.presentation.core.components.settings.SliderWithTitle
import com.classictoon.novel.ui.main.MainEvent
import com.classictoon.novel.ui.main.MainModel
import com.classictoon.novel.ui.theme.ExpandingTransition

@Composable
fun BrowseGridSizeOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    ExpandingTransition(visible = state.value.browseLayout == BrowseLayout.GRID) {
        SliderWithTitle(
            value = state.value.browseGridSize
                    to " ${stringResource(R.string.browse_grid_size_per_row)}",
            valuePlaceholder = stringResource(id = R.string.browse_grid_size_auto),
            showPlaceholder = state.value.browseAutoGridSize,
            fromValue = 0,
            toValue = 10,
            title = stringResource(id = R.string.browse_grid_size_option),
            onValueChange = {
                mainModel.onEvent(MainEvent.OnChangeBrowseAutoGridSize(it == 0))
                mainModel.onEvent(
                    MainEvent.OnChangeBrowseGridSize(it)
                )
            }
        )
    }
}
