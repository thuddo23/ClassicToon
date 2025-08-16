/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.repository

import com.classictoon.novel.data.local.dto.BookEntity
import com.classictoon.novel.domain.library.book.Book

interface ServerBookRepository {
    
    // Feed methods
    suspend fun getTrendingBooks(limit: Int = 20): List<Book>
    
    suspend fun getTopPicks(limit: Int = 20): List<Book>
    
    suspend fun getCategoryRanking(category: String, period: String = "weekly"): List<Book>
    
    suspend fun getNewestBooks(limit: Int = 20): List<Book>
    
    // Discovery methods
    suspend fun getBooks(
        page: Int = 1,
        limit: Int = 20,
        searchQuery: String? = null,
        category: String? = null,
        type: String? = null,
        sort: String? = null
    ): List<Book>
    
    suspend fun getBookById(bookId: String): Book?
    
    suspend fun getBookDetail(bookId: String): Book?
    
    suspend fun getBookContent(bookId: String): String
    
    // Legacy methods for backward compatibility
    suspend fun searchBooks(
        query: String,
        page: Int = 1,
        limit: Int = 20
    ): List<Book>
    
    suspend fun getBooksByGenre(
        genre: String,
        page: Int = 1,
        limit: Int = 20
    ): List<Book>
}
