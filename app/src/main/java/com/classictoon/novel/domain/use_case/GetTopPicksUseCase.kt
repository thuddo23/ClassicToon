/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclarite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.use_case

import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.repository.ServerBookRepository
import javax.inject.Inject

class GetTopPicksUseCase @Inject constructor(
    private val repository: ServerBookRepository
) {
    
    suspend operator fun invoke(limit: Int = 20): List<Book> {
        return repository.getTopPicks(limit)
    }
}
