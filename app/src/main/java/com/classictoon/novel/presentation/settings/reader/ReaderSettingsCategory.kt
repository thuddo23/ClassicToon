/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("FunctionName")

package com.classictoon.novel.presentation.settings.reader

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.classictoon.novel.presentation.settings.reader.chapters.ChaptersSubcategory
import com.classictoon.novel.presentation.settings.reader.font.FontSubcategory
import com.classictoon.novel.presentation.settings.reader.images.ImagesSubcategory
import com.classictoon.novel.presentation.settings.reader.misc.MiscSubcategory
import com.classictoon.novel.presentation.settings.reader.padding.PaddingSubcategory
import com.classictoon.novel.presentation.settings.reader.progress.ProgressSubcategory
import com.classictoon.novel.presentation.settings.reader.reading_mode.ReadingModeSubcategory
import com.classictoon.novel.presentation.settings.reader.reading_speed.ReadingSpeedSubcategory
import com.classictoon.novel.presentation.settings.reader.system.SystemSubcategory
import com.classictoon.novel.presentation.settings.reader.text.TextSubcategory
import com.classictoon.novel.presentation.settings.reader.translator.TranslatorSubcategory

fun LazyListScope.ReaderSettingsCategory(
    titleColor: @Composable () -> Color = { MaterialTheme.colorScheme.primary }
) {
    FontSubcategory(
        titleColor = titleColor
    )
    TextSubcategory(
        titleColor = titleColor
    )
    ImagesSubcategory(
        titleColor = titleColor
    )
    ChaptersSubcategory(
        titleColor = titleColor
    )
    ReadingModeSubcategory(
        titleColor = titleColor
    )
    PaddingSubcategory(
        titleColor = titleColor
    )
    SystemSubcategory(
        titleColor = titleColor
    )
    ReadingSpeedSubcategory(
        titleColor = titleColor
    )
    ProgressSubcategory(
        titleColor = titleColor
    )
    TranslatorSubcategory(
        titleColor = titleColor
    )
    MiscSubcategory(
        titleColor = titleColor,
        showDivider = false
    )
}
