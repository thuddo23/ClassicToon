/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclarite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel

import com.classictoon.novel.data.remote.dto.BookListResponse
import com.classictoon.novel.data.remote.dto.BookMetrics
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.library.category.Category
import com.classictoon.novel.domain.ui.UIText

/**
 * Test configuration and utility functions for unit tests
 */
object TestConfig {
    
    /**
     * Creates a test BookListResponse with default values
     */
    fun createTestBookListResponse(
        id: Int = 0,
        title: String = "Test Book",
        type: String = "html",
        coverUrl: String? = "https://example.com/cover.jpg",
        categories: List<String> = listOf("fantasy"),
        publishedAt: String = "2024-01-01T00:00:00Z",
        metrics: BookMetrics = BookMetrics(reads = 1000, likes = 50, ratingAvg = 4.5)
    ): BookListResponse {
        return BookListResponse(
            id = id,
            title = title,
            type = type,
            coverUrl = coverUrl,
            categories = categories,
            publishedAt = publishedAt,
            metrics = metrics
        )
    }
    
    /**
     * Creates a test Book with default values
     */
    fun createTestBook(
        id: Int = 1,
        title: String = "Test Book",
        author: String = "Test Author",
        description: String = "Test Description",
        filePath: String = "/test/path",
        coverImage: String? = "https://example.com/cover.jpg",
        scrollIndex: Int = 0,
        scrollOffset: Int = 0,
        progress: Float = 0f,
        lastOpened: Long? = null,
        category: Set<Category> = setOf(Category.FANTASY)
    ): Book {
        return Book(
            id = id,
            title = title,
            author = UIText.StringValue(author),
            description = description,
            filePath = filePath,
            coverImage = coverImage?.let { android.net.Uri.parse(it) },
            scrollIndex = scrollIndex,
            scrollOffset = scrollOffset,
            progress = progress,
            lastOpened = lastOpened,
            category = category
        )
    }
    
    /**
     * Creates a list of test books
     */
    fun createTestBooks(count: Int, startId: Int = 1): List<Book> {
        return List(count) { index ->
            createTestBook(
                id = startId + index,
                title = "Test Book ${startId + index}",
                author = "Test Author ${startId + index}",
                description = "Test Description ${startId + index}",
                filePath = "/test/path/${startId + index}",
                category = setOf(Category.values()[index % Category.values().size])
            )
        }
    }
    
    /**
     * Creates a list of test BookListResponse objects
     */
    fun createTestBookListResponses(count: Int, startId: Int = 1): List<BookListResponse> {
        return List(count) { index ->
            createTestBookListResponse(
                id = startId + index,
                title = "Test Book ${startId + index}",
                categories = listOf("fantasy", "adventure"),
                metrics = BookMetrics(
                    reads = 1000 + (index * 100),
                    likes = 50 + (index * 10),
                    ratingAvg = 4.0 + (index * 0.1)
                )
            )
        }
    }
    
    /**
     * Test categories for mapping tests
     */
    val testCategories = listOf(
        "fantasy" to Category.FANTASY,
        "romance" to Category.ROMANCE,
        "action" to Category.ACTION,
        "thriller" to Category.THRILLER,
        "comedy" to Category.COMEDY,
        "drama" to Category.DRAMA,
        "mystery" to Category.MYSTERY,
        "science-fiction" to Category.SCIENCE_FICTION,
        "adventure" to Category.ACTION,
        "classic" to Category.ROMANCE,
        "biography" to Category.DRAMA
    )
    
    /**
     * Test metrics for various scenarios
     */
    object TestMetrics {
        val highRated = BookMetrics(reads = 10000, likes = 500, ratingAvg = 4.8)
        val mediumRated = BookMetrics(reads = 5000, likes = 250, ratingAvg = 4.2)
        val lowRated = BookMetrics(reads = 1000, likes = 50, ratingAvg = 3.5)
        val noLikes = BookMetrics(reads = 2000, likes = null, ratingAvg = 4.0)
        val noRating = BookMetrics(reads = 1500, likes = 75, ratingAvg = null)
    }
}
