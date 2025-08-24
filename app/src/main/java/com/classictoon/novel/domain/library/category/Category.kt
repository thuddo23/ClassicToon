/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.library.category

import androidx.compose.runtime.Immutable

@Immutable
enum class Category {
    FANTASY,
    ROMANCE,
    ACTION,
    THRILLER,
    COMEDY,
    DRAMA,
    MYSTERY,
    SCIENCE_FICTION,
    ALL,
}

val DEFAULT_CATEGORY = setOf(Category.ALL)
