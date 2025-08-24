/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.library.book

import androidx.compose.runtime.Immutable
import com.classictoon.novel.domain.util.CoverImage

@Immutable
data class BookWithCover(
    val book: Book,
    val coverImage: CoverImage?
)
