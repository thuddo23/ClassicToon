/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.book_info

import androidx.compose.runtime.Immutable
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.util.BottomSheet
import com.classictoon.novel.domain.util.Dialog
import com.classictoon.novel.presentation.core.constants.provideEmptyBook

@Immutable
data class BookInfoState(
    val book: Book = provideEmptyBook(),

    val canResetCover: Boolean = false,

    val dialog: Dialog? = null,
    val bottomSheet: BottomSheet? = null
)
