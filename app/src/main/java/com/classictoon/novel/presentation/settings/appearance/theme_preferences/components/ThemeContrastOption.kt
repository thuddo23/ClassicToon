/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.settings.appearance.theme_preferences.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.classictoon.novel.R
import com.classictoon.novel.domain.ui.ButtonItem
import com.classictoon.novel.domain.ui.ThemeContrast
import com.classictoon.novel.domain.ui.isDark
import com.classictoon.novel.domain.ui.isPureDark
import com.classictoon.novel.presentation.core.components.settings.SegmentedButtonWithTitle
import com.classictoon.novel.ui.main.MainEvent
import com.classictoon.novel.ui.main.MainModel
import com.classictoon.novel.ui.theme.BookStoryTheme
import com.classictoon.novel.ui.theme.ExpandingTransition

@Composable
fun ThemeContrastOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    val themeContrastTheme = remember { mutableStateOf(state.value.theme) }
    LaunchedEffect(state.value.theme) {
        if (state.value.theme.hasThemeContrast) {
            themeContrastTheme.value = state.value.theme
        }
    }

    BookStoryTheme(
        theme = themeContrastTheme.value,
        isDark = state.value.darkTheme.isDark(),
        isPureDark = state.value.pureDark.isPureDark(context = LocalContext.current),
        themeContrast = state.value.themeContrast
    ) {
        ExpandingTransition(visible = state.value.theme.hasThemeContrast) {
            SegmentedButtonWithTitle(
                title = stringResource(id = R.string.theme_contrast_option),
                enabled = state.value.theme.hasThemeContrast,
                buttons = ThemeContrast.entries.map {
                    ButtonItem(
                        id = it.toString(),
                        title = when (it) {
                            ThemeContrast.STANDARD -> stringResource(id = R.string.theme_contrast_standard)
                            ThemeContrast.MEDIUM -> stringResource(id = R.string.theme_contrast_medium)
                            ThemeContrast.HIGH -> stringResource(id = R.string.theme_contrast_high)
                        },
                        textStyle = MaterialTheme.typography.labelLarge,
                        selected = it == state.value.themeContrast
                    )
                }
            ) {
                mainModel.onEvent(
                    MainEvent.OnChangeThemeContrast(
                        it.id
                    )
                )
            }
        }
    }
}
