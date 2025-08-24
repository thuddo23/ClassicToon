/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("FunctionName")

package com.classictoon.novel.presentation.settings.reader.misc

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.classictoon.novel.R
import com.classictoon.novel.presentation.settings.components.SettingsSubcategory
import com.classictoon.novel.presentation.settings.reader.misc.components.FullscreenOption
import com.classictoon.novel.presentation.settings.reader.misc.components.HideBarsOnFastScrollOption
import com.classictoon.novel.presentation.settings.reader.misc.components.KeepScreenOnOption

fun LazyListScope.MiscSubcategory(
    titleColor: @Composable () -> Color = { MaterialTheme.colorScheme.primary },
    title: @Composable () -> String = { stringResource(id = R.string.misc_reader_settings) },
    showTitle: Boolean = true,
    showDivider: Boolean = true
) {
    SettingsSubcategory(
        titleColor = titleColor,
        title = title,
        showTitle = showTitle,
        showDivider = showDivider,
    ) {
        item {
            FullscreenOption()
        }

        item {
            KeepScreenOnOption()
        }

        item {
            HideBarsOnFastScrollOption()
        }
    }
}
