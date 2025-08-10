/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.repository

import com.classictoon.novel.data.local.dto.BookEntity

interface ServerBookRepository {
    
    suspend fun getBooks(
        page: Int = 1,
        limit: Int = 20,
        searchQuery: String? = null,
        genre: String? = null
    ): List<BookEntity>
    
    suspend fun getBookById(
        bookId: String
    ): BookEntity?
    
    suspend fun getBookContent(
        bookId: String
    ): String // Returns HTML content
    
    suspend fun searchBooks(
        query: String,
        page: Int = 1,
        limit: Int = 20
    ): List<BookEntity>
    
    suspend fun getBooksByGenre(
        genre: String,
        page: Int = 1,
        limit: Int = 20
    ): List<BookEntity>
}
