/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.reader

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Immutable
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.reader.Checkpoint
import com.classictoon.novel.domain.reader.ReaderText
import com.classictoon.novel.domain.reader.ReaderText.Chapter
import com.classictoon.novel.domain.ui.UIText
import com.classictoon.novel.domain.util.BottomSheet
import com.classictoon.novel.domain.util.Drawer
import com.classictoon.novel.presentation.core.constants.provideEmptyBook

@Immutable
data class ReaderState(
    val book: Book = provideEmptyBook(),
    val text: List<ReaderText> = emptyList(),
    val listState: LazyListState = LazyListState(),

    val currentChapter: Chapter? = null,
    val currentChapterProgress: Float = 0f,

    val errorMessage: UIText? = null,
    val isLoading: Boolean = true,

    val showMenu: Boolean = false,
    val checkpoint: Checkpoint = Checkpoint(0, 0),
    val lockMenu: Boolean = false,

    val bottomSheet: BottomSheet? = null,
    val drawer: Drawer? = null
)
