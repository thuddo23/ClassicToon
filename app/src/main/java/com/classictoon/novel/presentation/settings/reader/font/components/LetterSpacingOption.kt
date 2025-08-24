/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.settings.reader.font.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.classictoon.novel.R
import com.classictoon.novel.presentation.core.components.settings.SliderWithTitle
import com.classictoon.novel.ui.main.MainEvent
import com.classictoon.novel.ui.main.MainModel

@Composable
fun LetterSpacingOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    SliderWithTitle(
        value = state.value.letterSpacing to "pt",
        fromValue = -8,
        toValue = 16,
        title = stringResource(id = R.string.letter_spacing_option),
        onValueChange = {
            mainModel.onEvent(
                MainEvent.OnChangeLetterSpacing(it)
            )
        }
    )
}
