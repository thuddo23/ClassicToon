/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.repository

import com.classictoon.novel.data.remote.ApiService
import com.classictoon.novel.data.remote.MockApiService
import com.classictoon.novel.data.mapper.book.BookMapper
import com.classictoon.novel.data.remote.dto.BookMetrics
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.repository.ServerBookRepository
import com.classictoon.novel.data.remote.dto.FeedBookItem
import com.classictoon.novel.data.remote.dto.RemoteBookResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerBookRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mockApiService: MockApiService, // Keep for fallback
    private val bookMapper: BookMapper
) : ServerBookRepository {

    // Feed methods
    override suspend fun getTrendingBooks(limit: Int): List<Book> {
        return try {
            val response = apiService.getTrendingBooks(limit)
            response.items.map { bookMapper.toBook(it.toRemoteBookResponse()) }
        } catch (e: Exception) {
            mockApiService.getTrendingBooks(limit).items.map { bookMapper.toBook(it.toRemoteBookResponse()) }
        }
    }

    override suspend fun getTopPicks(limit: Int): List<Book> {
        return try {
            val response = apiService.getTopPicks(limit)
            response.items.map { bookMapper.toBook(it.book.toRemoteBookResponse()) }
        } catch (e: Exception) {
            mockApiService.getTopPicks(limit).items.map { bookMapper.toBook(it.book.toRemoteBookResponse()) }
        }
    }

    override suspend fun getCategoryRanking(category: String, period: String): List<Book> {
        return try {
            val response = apiService.getCategoryRanking(category, period)
            response.items.map { bookMapper.toBook(it.book.toRemoteBookResponse()) }
        } catch (e: Exception) {
            mockApiService.getCategoryRanking(category, period).items.map { bookMapper.toBook(it.book.toRemoteBookResponse()) }
        }
    }

    override suspend fun getNewestBooks(limit: Int): List<Book> {
        return try {
            val response = apiService.getNewestBooks(limit)
            response.items.map { bookMapper.toBook(it.toRemoteBookResponse()) }
        } catch (e: Exception) {
            mockApiService.getNewestBooks(limit).items.map { bookMapper.toBook(it.toRemoteBookResponse()) }
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
            response.items.map { bookMapper.toBook(it.toRemoteBookResponse()) }
        } catch (e: Exception) {
            mockApiService.getBooks(page, limit, searchQuery, category, type, sort).items.map { bookMapper.toBook(it.toRemoteBookResponse()) }
        }
    }

    override suspend fun getBookById(bookId: String): Book? {
        return try {
            val response = apiService.getBookDetail(bookId)
            bookMapper.toBook(response.toRemoteBookResponse())
        } catch (e: Exception) {
            mockApiService.getBookDetail(bookId)?.let { bookMapper.toBook(it.toRemoteBookResponse()) }
        }
    }

    override suspend fun getBookDetail(bookId: String): Book? {
        return getBookById(bookId) // For now, use the same implementation
    }

    override suspend fun getBookContent(bookId: String): String {
        return try {
            apiService.getBookContent(bookId)
        } catch (e: Exception) {
            mockApiService.getBookContent(bookId)
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

// Extension functions to convert between DTOs
private fun FeedBookItem.toRemoteBookResponse(): com.classictoon.novel.data.remote.dto.RemoteBookResponse {
    return RemoteBookResponse(
        id = this.id,
        title = this.title,
        type = this.type ?: "html",
        coverUrl = this.coverUrl ?: "",
        description = "Description for ${this.title}",
        authors = listOf("Unknown Author"),
        categories = this.categories ?: emptyList(),
        tags = this.tags ?: emptyList(),
        language = "en",
        publishedAt = this.publishedAt ?: "2024-01-01T00:00:00Z",
        createdAt = "2024-01-01T00:00:00Z",
        updatedAt = "2024-01-01T00:00:00Z",
        metrics = this.metrics ?: com.classictoon.novel.data.remote.dto.BookMetrics(reads = 0)
    )
}

private fun com.classictoon.novel.data.remote.dto.TopPickBook.toRemoteBookResponse(): com.classictoon.novel.data.remote.dto.RemoteBookResponse {
    return com.classictoon.novel.data.remote.dto.RemoteBookResponse(
        id = this.id,
        title = this.title,
        type = "html",
        coverUrl = this.coverUrl,
        description = "Description for ${this.title}",
        authors = listOf("Unknown Author"),
        categories = emptyList(),
        tags = emptyList(),
        language = "en",
        publishedAt = "2024-01-01T00:00:00Z",
        createdAt = "2024-01-01T00:00:00Z",
        updatedAt = "2024-01-01T00:00:00Z",
        metrics = com.classictoon.novel.data.remote.dto.BookMetrics(reads = 0)
    )
}

private fun com.classictoon.novel.data.remote.dto.RankingBook.toRemoteBookResponse(): com.classictoon.novel.data.remote.dto.RemoteBookResponse {
    return com.classictoon.novel.data.remote.dto.RemoteBookResponse(
        id = this.id,
        title = this.title,
        type = "html",
        coverUrl = this.coverUrl,
        description = "Description for ${this.title}",
        authors = listOf("Unknown Author"),
        categories = emptyList(),
        tags = emptyList(),
        language = "en",
        publishedAt = "2024-01-01T00:00:00Z",
        createdAt = "2024-01-01T00:00:00Z",
        updatedAt = "2024-01-01T00:00:00Z",
        metrics = BookMetrics(reads = 0)
    )
}

private fun com.classictoon.novel.data.remote.dto.RemoteBookDetailResponse.toRemoteBookResponse(): RemoteBookResponse {
    return RemoteBookResponse(
        id = this.id,
        title = this.title,
        type = this.type,
        coverUrl = this.coverUrl,
        description = this.description,
        authors = this.authors,
        categories = this.categories,
        tags = this.tags,
        language = this.language,
        publishedAt = this.publishedAt,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        metrics = this.metrics
    )
}
