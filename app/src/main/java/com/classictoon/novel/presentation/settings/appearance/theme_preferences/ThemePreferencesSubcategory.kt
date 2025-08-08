/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("FunctionName")

package com.classictoon.novel.presentation.settings.appearance.theme_preferences

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.classictoon.novel.R
import com.classictoon.novel.presentation.settings.appearance.theme_preferences.components.AbsoluteDarkOption
import com.classictoon.novel.presentation.settings.appearance.theme_preferences.components.AppThemeOption
import com.classictoon.novel.presentation.settings.appearance.theme_preferences.components.DarkThemeOption
import com.classictoon.novel.presentation.settings.appearance.theme_preferences.components.PureDarkOption
import com.classictoon.novel.presentation.settings.appearance.theme_preferences.components.ThemeContrastOption
import com.classictoon.novel.presentation.settings.components.SettingsSubcategory

fun LazyListScope.ThemePreferencesSubcategory(
    titleColor: @Composable () -> Color = { MaterialTheme.colorScheme.primary },
    title: @Composable () -> String = { stringResource(id = R.string.theme_appearance_settings) },
    showTitle: Boolean = true,
    showDivider: Boolean = true
) {
    SettingsSubcategory(
        titleColor = titleColor,
        title = title,
        showTitle = showTitle,
        showDivider = showDivider
    ) {
        item {
            DarkThemeOption()
        }

        item {
            AppThemeOption()
        }

        item {
            ThemeContrastOption()
        }

        item {
            PureDarkOption()
        }

        item {
            AbsoluteDarkOption()
        }
    }
}
