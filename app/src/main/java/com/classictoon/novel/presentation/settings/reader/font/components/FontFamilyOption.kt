/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.settings.reader.font.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.classictoon.novel.R
import com.classictoon.novel.domain.ui.ButtonItem
import com.classictoon.novel.presentation.core.components.settings.ChipsWithTitle
import com.classictoon.novel.presentation.core.constants.provideFonts
import com.classictoon.novel.ui.main.MainEvent
import com.classictoon.novel.ui.main.MainModel

@Composable
fun FontFamilyOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    val fontFamily = remember(state.value.fontFamily) {
        provideFonts().run {
            find {
                it.id == state.value.fontFamily
            } ?: get(0)
        }
    }

    ChipsWithTitle(
        title = stringResource(id = R.string.font_family_option),
        chips = provideFonts()
            .map {
                ButtonItem(
                    id = it.id,
                    title = it.fontName.asString(),
                    textStyle = MaterialTheme.typography.labelLarge.copy(
                        fontFamily = it.font
                    ),
                    selected = it.id == fontFamily.id
                )
            },
        onClick = {
            mainModel.onEvent(
                MainEvent.OnChangeFontFamily(
                    it.id
                )
            )
        }
    )
}
