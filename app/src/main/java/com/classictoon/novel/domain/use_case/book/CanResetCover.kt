/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.use_case.book

import com.classictoon.novel.domain.repository.BookRepository
import javax.inject.Inject

class CanResetCover @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(bookId: Int): Boolean {
        return repository.canResetCover(bookId)
    }
}
