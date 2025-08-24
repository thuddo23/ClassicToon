/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("FunctionName")

package com.classictoon.novel.presentation.start

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import com.classictoon.novel.R
import com.classictoon.novel.presentation.settings.appearance.theme_preferences.ThemePreferencesSubcategory

fun LazyListScope.StartSettingsLayoutAppearance() {
    ThemePreferencesSubcategory(
        title = { stringResource(id = R.string.start_theme_preferences) },
        titleColor = { MaterialTheme.colorScheme.secondary },
        showDivider = false
    )
}
