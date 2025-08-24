/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.use_case.book

import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.repository.BookRepository
import com.classictoon.novel.domain.util.CoverImage
import javax.inject.Inject

class UpdateCoverImageOfBook @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(bookWithOldCover: Book, newCoverImage: CoverImage?) {
        repository.updateCoverImageOfBook(bookWithOldCover, newCoverImage)
    }
}
