/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.settings.reader.text.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.classictoon.novel.R
import com.classictoon.novel.domain.reader.ReaderTextAlignment
import com.classictoon.novel.presentation.core.components.settings.SliderWithTitle
import com.classictoon.novel.ui.main.MainEvent
import com.classictoon.novel.ui.main.MainModel
import com.classictoon.novel.ui.theme.ExpandingTransition

@Composable
fun ParagraphIndentationOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    ExpandingTransition(
        visible = state.value.textAlignment != ReaderTextAlignment.CENTER &&
                state.value.textAlignment != ReaderTextAlignment.END
    ) {
        SliderWithTitle(
            value = state.value.paragraphIndentation to "pt",
            fromValue = 0,
            toValue = 12,
            title = stringResource(id = R.string.paragraph_indentation_option),
            onValueChange = {
                mainModel.onEvent(
                    MainEvent.OnChangeParagraphIndentation(it)
                )
            }
        )
    }
}
