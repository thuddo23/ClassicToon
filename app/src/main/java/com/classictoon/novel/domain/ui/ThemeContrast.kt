/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.ui

import androidx.compose.runtime.Immutable

@Immutable
enum class ThemeContrast {
    STANDARD,
    MEDIUM,
    HIGH
}

fun String.toThemeContrast(): ThemeContrast {
    return ThemeContrast.valueOf(this)
}
