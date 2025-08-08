/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.library.category

import androidx.compose.runtime.Immutable
import com.classictoon.novel.domain.library.book.SelectableBook
import com.classictoon.novel.domain.ui.UIText

@Immutable
data class CategoryWithBooks(
    val category: Category,
    val title: UIText,
    val books: List<SelectableBook>
)
