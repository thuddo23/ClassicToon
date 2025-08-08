/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.settings.reader.progress.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.classictoon.novel.R
import com.classictoon.novel.domain.reader.ReaderProgressCount
import com.classictoon.novel.domain.ui.ButtonItem
import com.classictoon.novel.presentation.core.components.settings.SegmentedButtonWithTitle
import com.classictoon.novel.ui.main.MainEvent
import com.classictoon.novel.ui.main.MainModel

@Composable
fun ProgressCountOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    SegmentedButtonWithTitle(
        title = stringResource(id = R.string.progress_count_option),
        buttons = ReaderProgressCount.entries.map {
            ButtonItem(
                id = it.toString(),
                title = when (it) {
                    ReaderProgressCount.PERCENTAGE -> stringResource(id = R.string.progress_count_percentage)
                    ReaderProgressCount.QUANTITY -> stringResource(id = R.string.progress_count_quantity)
                },
                textStyle = MaterialTheme.typography.labelLarge,
                selected = it == state.value.progressCount
            )
        },
        onClick = {
            mainModel.onEvent(
                MainEvent.OnChangeProgressCount(
                    it.id
                )
            )
        }
    )
}
