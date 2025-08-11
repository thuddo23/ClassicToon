/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.use_case

import com.classictoon.novel.data.local.dto.BookEntity
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.repository.ServerBookRepository
import javax.inject.Inject

class GetServerBooksUseCase @Inject constructor(
    private val repository: ServerBookRepository
) {
    
    suspend operator fun invoke(
        page: Int = 1,
        limit: Int = 20,
        searchQuery: String? = null,
        genre: String? = null
    ): List<Book> {
        return repository.getBooks(page, limit, searchQuery, genre)
    }
}
