/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("FunctionName")

package com.classictoon.novel.presentation.settings.reader.text

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.classictoon.novel.R
import com.classictoon.novel.presentation.settings.components.SettingsSubcategory
import com.classictoon.novel.presentation.settings.reader.text.components.LineHeightOption
import com.classictoon.novel.presentation.settings.reader.text.components.ParagraphHeightOption
import com.classictoon.novel.presentation.settings.reader.text.components.ParagraphIndentationOption
import com.classictoon.novel.presentation.settings.reader.text.components.TextAlignmentOption

fun LazyListScope.TextSubcategory(
    titleColor: @Composable () -> Color = { MaterialTheme.colorScheme.primary },
    title: @Composable () -> String = { stringResource(id = R.string.text_reader_settings) },
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
            TextAlignmentOption()
        }

        item {
            LineHeightOption()
        }

        item {
            ParagraphHeightOption()
        }

        item {
            ParagraphIndentationOption()
        }
    }
}
