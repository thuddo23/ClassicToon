/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.repository

import com.classictoon.novel.data.remote.ApiService
import com.classictoon.novel.data.remote.MockApiService
import com.classictoon.novel.data.mapper.book.BookMapper
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.repository.ServerBookRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerBookRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mockApiService: MockApiService, // Keep for fallback
    private val bookMapper: BookMapper // Map to BookEntity,
) : ServerBookRepository {

    override suspend fun getBooks(
        page: Int,
        limit: Int,
        searchQuery: String?,
        genre: String?
    ): List<Book> {
        return try {
            apiService.getBooks(page, limit, searchQuery, genre).map { bookMapper.toBook(it) }
        } catch (e: Exception) {
            // Fallback to mock service if real API fails
            mockApiService.getBooks(page, limit, searchQuery, genre).map { bookMapper.toBook(it) }
        }
    }

    override suspend fun getBookById(bookId: String): Book? {
        return try {
            // Note: Real API doesn't have getBookById endpoint, so we'll use mock for now
            // In a real implementation, you might want to add this endpoint or fetch from the books list
            mockApiService.getBookById(bookId)?.let { bookMapper.toBook(it) }
        } catch (e: Exception) {
            mockApiService.getBookById(bookId)?.let { bookMapper.toBook(it) }
        }
    }

    override suspend fun getBookContent(bookId: String): String {
        return try {
            apiService.getBookContent(bookId)
        } catch (e: Exception) {
            // Fallback to mock service if real API fails
            mockApiService.getBookContent(bookId)
        }
    }

    override suspend fun searchBooks(
        query: String,
        page: Int,
        limit: Int
    ): List<Book> {
        return try {
            apiService.getBooks(page, limit, query, null).map { bookMapper.toBook(it) }
        } catch (e: Exception) {
            mockApiService.getBooks(page, limit, query, null).map { bookMapper.toBook(it) }
        }
    }

    override suspend fun getBooksByGenre(
        genre: String,
        page: Int,
        limit: Int
    ): List<Book> {
        return try {
            apiService.getBooks(page, limit, null, genre).map { bookMapper.toBook(it) }
        } catch (e: Exception) {
            mockApiService.getBooks(page, limit, null, genre).map { bookMapper.toBook(it) }
        }
    }
}
