/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.settings.appearance.theme_preferences.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.classictoon.novel.R
import com.classictoon.novel.domain.ui.isDark
import com.classictoon.novel.domain.ui.isPureDark
import com.classictoon.novel.presentation.core.components.settings.SwitchWithTitle
import com.classictoon.novel.ui.main.MainEvent
import com.classictoon.novel.ui.main.MainModel
import com.classictoon.novel.ui.theme.ExpandingTransition

@Composable
fun AbsoluteDarkOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ExpandingTransition(
        visible = state.value.pureDark.isPureDark(context)
                && state.value.darkTheme.isDark()
    ) {
        SwitchWithTitle(
            selected = state.value.absoluteDark,
            title = stringResource(id = R.string.absolute_dark_option),
            description = stringResource(id = R.string.absolute_dark_option_desc),
            onClick = {
                mainModel.onEvent(
                    MainEvent.OnChangeAbsoluteDark(
                        !state.value.absoluteDark
                    )
                )
            }
        )
    }
}
