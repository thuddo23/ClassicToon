/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.settings.reader.reading_mode.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.classictoon.novel.R
import com.classictoon.novel.domain.reader.ReaderHorizontalGesture
import com.classictoon.novel.presentation.core.components.settings.SliderWithTitle
import com.classictoon.novel.ui.main.MainEvent
import com.classictoon.novel.ui.main.MainModel
import com.classictoon.novel.ui.theme.ExpandingTransition

@Composable
fun HorizontalGestureScrollOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    ExpandingTransition(
        visible = when (state.value.horizontalGesture) {
            ReaderHorizontalGesture.OFF -> false
            else -> true
        }
    ) {
        SliderWithTitle(
            value = state.value.horizontalGestureScroll to "%",
            toValue = 100,
            title = stringResource(id = R.string.horizontal_gesture_scroll_option),
            onValueChange = {
                mainModel.onEvent(
                    MainEvent.OnChangeHorizontalGestureScroll(it)
                )
            }
        )
    }
}
