/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.repository

import com.classictoon.novel.data.mapper.book.BookMapper
import com.classictoon.novel.data.remote.ApiService
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.repository.ServerBookRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerBookRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val bookMapper: BookMapper
) : ServerBookRepository {

    // Feed methods
    override suspend fun getTrendingBooks(limit: Int): List<Book> {
        return try {
            val response = apiService.getTrendingBooks(limit)
            response.map { bookMapper.toBook(it) }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTopPicks(limit: Int): List<Book> {
        return try {
            val response = apiService.getTopPicks(limit)
            response.items.map { bookMapper.toBook(it.book) }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCategoryRanking(category: String, period: String): List<Book> {
        return try {
            val response = apiService.getCategoryRanking(category, period)
            response.items.map { bookMapper.toBook(it.book) }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getNewestBooks(limit: Int): List<Book> {
        return try {
            val response = apiService.getNewestBooks(limit)
            response.map { bookMapper.toBook(it) }
        } catch (e: Exception) {
            throw e
        }
    }

    // Discovery methods
    override suspend fun getBooks(
        page: Int,
        limit: Int,
        searchQuery: String?,
        category: String?,
        type: String?,
        sort: String?
    ): List<Book> {
        return try {
            val response = apiService.getBooks(page, limit, searchQuery, category, type, sort)
            response.items.map { bookMapper.toBook(it) }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getBookById(bookId: Int): Book? {
        return try {
            val response = apiService.getBookDetail(bookId)
            bookMapper.toBook(response)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getBookDetail(bookId: Int): Book? {
        return getBookById(bookId) // For now, use the same implementation
    }

    override suspend fun getBookContent(bookId: Int): String {
        return try {
            apiService.getBookContent(bookId)
        } catch (e: Exception) {
            throw e
        }
    }

    // Legacy methods for backward compatibility
    override suspend fun searchBooks(
        query: String,
        page: Int,
        limit: Int
    ): List<Book> {
        return getBooks(page, limit, query, null, null, null)
    }

    override suspend fun getBooksByGenre(
        genre: String,
        page: Int,
        limit: Int
    ): List<Book> {
        return getBooks(page, limit, null, genre, null, null)
    }
}