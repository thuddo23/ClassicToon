/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclarite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel

import android.net.Uri
import com.classictoon.novel.data.remote.dto.BookDetailResponse
import com.classictoon.novel.data.remote.dto.BookListResponse
import com.classictoon.novel.data.remote.dto.BookMetrics
import com.classictoon.novel.data.remote.dto.BookSource
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.library.category.Category
import com.classictoon.novel.domain.ui.UIText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Test utilities and extensions for unit tests
 */
object TestUtils {
    
    /**
     * Creates a test BookListResponse with default values
     */
    fun createTestBookListResponse(
        id: Int = 1,
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
     * Creates a test BookDetailResponse with default values
     */
    fun createTestBookDetailResponse(
        id: Int = 1,
        title: String = "Test Detail Book",
        type: String = "html",
        description: String = "A test description",
        coverUrl: String = "https://example.com/detail.jpg",
        language: String = "en",
        authors: List<String> = listOf("Test Author"),
        categories: List<String> = listOf("fantasy"),
        tags: List<String> = listOf("adventure"),
        publishedAt: String = "2024-01-01T00:00:00Z",
        createdAt: String = "2024-01-01T00:00:00Z",
        updatedAt: String = "2024-01-01T00:00:00Z",
        metrics: BookMetrics = BookMetrics(reads = 2000, likes = 100, ratingAvg = 4.8),
        source: BookSource? = BookSource(
            id = 1,
            bookId = id,
            url = "https://example.com/book.json",
            sizeBytes = 1024000
        )
    ): BookDetailResponse {
        return BookDetailResponse(
            id = id,
            title = title,
            type = type,
            description = description,
            coverUrl = coverUrl,
            language = language,
            authors = authors,
            categories = categories,
            tags = tags,
            publishedAt = publishedAt,
            createdAt = createdAt,
            updatedAt = updatedAt,
            metrics = metrics,
            source = source
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
            coverImage = coverImage?.let { Uri.parse(it) },
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

/**
 * Test dispatcher rule for coroutines testing
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {
    
    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }
    
    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}

/**
 * Extension functions for testing
 */
object TestExtensions {
    
    /**
     * Creates a test Book with random data
     */
    fun createRandomTestBook(id: Int = (1..1000).random()): Book {
        val titles = listOf("The Great Adventure", "Mystery Manor", "Fantasy Quest", "Romance in Paris", "Thriller Night")
        val authors = listOf("John Doe", "Jane Smith", "Bob Johnson", "Alice Brown", "Charlie Wilson")
        val categories = listOf(Category.FANTASY, Category.ROMANCE, Category.ACTION, Category.THRILLER, Category.MYSTERY)
        
        return TestUtils.createTestBook(
            id = id,
            title = titles.random(),
            author = authors.random(),
            description = "A random test description for book $id",
            filePath = "/random/path/$id",
            coverImage = "https://picsum.photos/300/400?random=$id",
            category = setOf(categories.random())
        )
    }
    
    /**
     * Creates a list of random test books
     */
    fun createRandomTestBooks(count: Int): List<Book> {
        return List(count) { index ->
            createRandomTestBook(index + 1)
        }
    }
}
