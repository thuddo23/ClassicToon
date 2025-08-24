/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.reader

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.font.FontFamily
import com.classictoon.novel.domain.ui.UIText

@Immutable
data class FontWithName(
    val id: String,
    val fontName: UIText,
    val font: FontFamily
)
