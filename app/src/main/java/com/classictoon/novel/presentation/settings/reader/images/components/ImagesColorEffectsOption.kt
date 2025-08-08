/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.settings.reader.images.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.classictoon.novel.R
import com.classictoon.novel.domain.reader.ReaderColorEffects
import com.classictoon.novel.domain.ui.ButtonItem
import com.classictoon.novel.presentation.core.components.settings.ChipsWithTitle
import com.classictoon.novel.ui.main.MainEvent
import com.classictoon.novel.ui.main.MainModel
import com.classictoon.novel.ui.theme.ExpandingTransition

@Composable
fun ImagesColorEffectsOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    ExpandingTransition(visible = state.value.images) {
        ChipsWithTitle(
            title = stringResource(id = R.string.images_color_effects_option),
            chips = ReaderColorEffects.entries.map {
                ButtonItem(
                    id = it.toString(),
                    title = when (it) {
                        ReaderColorEffects.OFF -> {
                            stringResource(R.string.color_effects_off)
                        }

                        ReaderColorEffects.GRAYSCALE -> {
                            stringResource(R.string.color_effects_grayscale)
                        }

                        ReaderColorEffects.FONT -> {
                            stringResource(R.string.color_effects_font)
                        }

                        ReaderColorEffects.BACKGROUND -> {
                            stringResource(R.string.color_effects_background)
                        }
                    },
                    textStyle = MaterialTheme.typography.labelLarge,
                    selected = it == state.value.imagesColorEffects
                )
            },
            onClick = {
                mainModel.onEvent(
                    MainEvent.OnChangeImagesColorEffects(
                        it.id
                    )
                )
            }
        )
    }
}
