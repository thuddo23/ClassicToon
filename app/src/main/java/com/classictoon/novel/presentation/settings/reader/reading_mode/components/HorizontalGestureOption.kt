/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.settings.reader.reading_mode.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.classictoon.novel.R
import com.classictoon.novel.domain.reader.ReaderHorizontalGesture
import com.classictoon.novel.domain.ui.ButtonItem
import com.classictoon.novel.presentation.core.components.settings.ChipsWithTitle
import com.classictoon.novel.ui.main.MainEvent
import com.classictoon.novel.ui.main.MainModel

@Composable
fun HorizontalGestureOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    ChipsWithTitle(
        title = stringResource(id = R.string.horizontal_gesture_option),
        chips = ReaderHorizontalGesture.entries.map {
            ButtonItem(
                id = it.toString(),
                title = when (it) {
                    ReaderHorizontalGesture.OFF -> {
                        stringResource(R.string.horizontal_gesture_off)
                    }

                    ReaderHorizontalGesture.ON -> {
                        stringResource(R.string.horizontal_gesture_on)
                    }

                    ReaderHorizontalGesture.INVERSE -> {
                        stringResource(R.string.horizontal_gesture_inverse)
                    }
                },
                textStyle = MaterialTheme.typography.labelLarge,
                selected = it == state.value.horizontalGesture
            )
        },
        onClick = {
            mainModel.onEvent(
                MainEvent.OnChangeHorizontalGesture(
                    it.id
                )
            )
        }
    )
}
