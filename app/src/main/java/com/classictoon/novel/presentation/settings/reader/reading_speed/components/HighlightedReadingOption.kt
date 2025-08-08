/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.settings.reader.reading_speed.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.classictoon.novel.R
import com.classictoon.novel.presentation.core.components.settings.SwitchWithTitle
import com.classictoon.novel.ui.main.MainEvent
import com.classictoon.novel.ui.main.MainModel

@Composable
fun HighlightedReadingOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    SwitchWithTitle(
        selected = state.value.highlightedReading,
        title = stringResource(id = R.string.highlighted_reading_option),
        description = stringResource(id = R.string.highlighted_reading_option_desc),
        onClick = {
            mainModel.onEvent(
                MainEvent.OnChangeHighlightedReading(
                    !state.value.highlightedReading
                )
            )
        }
    )
}
