/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.reader

import androidx.compose.runtime.Immutable

@Immutable
enum class ReaderProgressCount {
    PERCENTAGE,
    QUANTITY
}

fun String.toProgressCount(): ReaderProgressCount {
    return ReaderProgressCount.valueOf(this)
}
