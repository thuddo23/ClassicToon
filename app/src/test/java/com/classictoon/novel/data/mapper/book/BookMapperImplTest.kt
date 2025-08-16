/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclarite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.mapper.book

import android.net.Uri
import com.classictoon.novel.R
import com.classictoon.novel.data.local.dto.BookEntity
import com.classictoon.novel.data.remote.dto.BookDetailResponse
import com.classictoon.novel.data.remote.dto.BookListResponse
import com.classictoon.novel.data.remote.dto.BookMetrics
import com.classictoon.novel.data.remote.dto.BookSource
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.library.category.Category
import com.classictoon.novel.domain.library.category.DEFAULT_CATEGORY
import com.classictoon.novel.domain.ui.UIText
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.random.Random

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class BookMapperImplTest {
    
    private lateinit var bookMapper: BookMapperImpl
    
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        bookMapper = BookMapperImpl()
    }
    
    @Test
    fun `toBookEntity should map Book to BookEntity correctly`() = runTest {
        // Given
        val book = Book(
            id = Random.Default.nextInt(100),
            title = "Test Book",
            author = UIText.StringValue("Test Author"),
            description = "Test Description",
            filePath = "/test/path",
            coverImage = Uri.parse("https://example.com/cover.jpg"),
            scrollIndex = 10,
            scrollOffset = 100,
            progress = 0.5f,
            lastOpened = 1234567890L,
            category = setOf(Category.FANTASY, Category.ROMANCE)
        )
        
        // When
        val result = bookMapper.toBookEntity(book)
        
        // Then
        assertEquals(1, result.id)
        assertEquals("Test Book", result.title)
        assertEquals("Test Author", result.author)
        assertEquals("Test Description", result.description)
        assertEquals("/test/path", result.filePath)
        assertEquals("https://example.com/cover.jpg", result.image)
        assertEquals(10, result.scrollIndex)
        assertEquals(100, result.scrollOffset)
        assertEquals(0.5f, result.progress)
        assertEquals(setOf(Category.FANTASY, Category.ROMANCE), result.category)
    }
    
    @Test
    fun `toBookEntity should handle null author and description`() = runTest {
        // Given
        val book = Book(
            id = Random.Default.nextInt(100),
            title = "Test Book",
            author = UIText.StringValue("Test Author"),
            description = null,
            filePath = "/test/path",
            coverImage = null,
            scrollIndex = 0,
            scrollOffset = 0,
            progress = 0f,
            lastOpened = null,
            category = emptySet()
        )
        
        // When
        val result = bookMapper.toBookEntity(book)
        
        // Then
        assertEquals("Test Author", result.author)
        assertNull(result.description)
        assertNull(result.image)
    }
    
    @Test
    fun `toBook from BookEntity should map correctly`() = runTest {
        // Given
        val bookEntity = BookEntity(
            id = Random.Default.nextInt(100),
            title = "Test Book",
            author = "Test Author",
            description = "Test Description",
            filePath = "/test/path",
            scrollIndex = 10,
            scrollOffset = 100,
            progress = 0.5f,
            image = "https://example.com/cover.jpg",
            category = setOf(Category.FANTASY)
        )
        
        // When
        val result = bookMapper.toBook(bookEntity)
        
        // Then
        assertEquals(1, result.id)
        assertEquals("Test Book", result.title)
        assertEquals("Test Author", result.author.getAsString())
        assertEquals("Test Description", result.description)
        assertEquals("/test/path", result.filePath)
        assertEquals("https://example.com/cover.jpg", result.coverImage?.toString())
        assertEquals(10, result.scrollIndex)
        assertEquals(100, result.scrollOffset)
        assertEquals(0.5f, result.progress)
        assertEquals(setOf(Category.FANTASY), result.category)
        assertNull(result.lastOpened)
    }
    
    @Test
    fun `toBook from BookEntity should use unknown author when author is null`() = runTest {
        // Given
        val bookEntity = BookEntity(
            id = Random.Default.nextInt(100),
            title = "Test Book",
            author = null,
            description = "Test Description",
            filePath = "/test/path",
            scrollIndex = 0,
            scrollOffset = 0,
            progress = 0f,
            image = null,
            category = emptySet()
        )
        
        // When
        val result = bookMapper.toBook(bookEntity)
        
        // Then
        assertTrue(result.author is UIText.StringResource)
        assertEquals(R.string.unknown_author, (result.author as UIText.StringResource).resId)
    }
    
    @Test
    fun `toBook from BookEntity should handle null image`() = runTest {
        // Given
        val bookEntity = BookEntity(
            id = Random.Default.nextInt(100),
            title = "Test Book",
            author = "Test Author",
            description = "Test Description",
            filePath = "/test/path",
            scrollIndex = 0,
            scrollOffset = 0,
            progress = 0f,
            image = null,
            category = emptySet()
        )
        
        // When
        val result = bookMapper.toBook(bookEntity)
        
        // Then
        assertNull(result.coverImage)
    }
    
    @Test
    fun `toBook from BookListResponse should map correctly`() = runTest {
        // Given
        val bookListResponse = BookListResponse(
            id = Random.Default.nextInt(100),
            title = "Test Book",
            type = "html",
            coverUrl = "https://example.com/cover.jpg",
            categories = listOf("fantasy", "adventure"),
            publishedAt = "2024-01-01T00:00:00Z",
            metrics = BookMetrics(reads = 1000, likes = 50, ratingAvg = 4.5)
        )
        
        // When
        val result = bookMapper.toBook(bookListResponse)
        
        // Then
        assertEquals("book123".hashCode(), result.id)
        assertEquals("Test Book", result.title)
        assertTrue(result.author is UIText.StringResource)
        assertEquals(R.string.unknown_author, (result.author as UIText.StringResource).resId)
        assertEquals("", result.description)
        assertEquals("", result.filePath)
        assertNull(result.coverImage)
        assertEquals(0, result.scrollIndex)
        assertEquals(0, result.scrollOffset)
        assertEquals(0f, result.progress)
        assertNull(result.lastOpened)
        assertEquals("https://example.com/cover.jpg", result.coverImage?.toString())
    }
    
    @Test
    fun `toBook from BookListResponse should handle null coverUrl`() = runTest {
        // Given
        val bookListResponse = BookListResponse(
            id = Random.Default.nextInt(100),
            title = "Test Book",
            type = "html",
            coverUrl = null,
            categories = listOf("fantasy"),
            publishedAt = "2024-01-01T00:00:00Z",
            metrics = BookMetrics(reads = 1000, likes = 50, ratingAvg = 4.5)
        )
        
        // When
        val result = bookMapper.toBook(bookListResponse)
        
        // Then
        assertNull(result.coverImage)
    }
    
    @Test
    fun `toBook from BookDetailResponse should map correctly`() = runTest {
        // Given
        val bookDetailResponse = BookDetailResponse(
            id = Random.Default.nextInt(100),
            title = "Detailed Book",
            type = "html",
            description = "A detailed description",
            coverUrl = "https://example.com/detail.jpg",
            language = "en",
            authors = listOf("John Doe"),
            categories = listOf("fantasy", "mystery"),
            tags = listOf("adventure", "magic"),
            publishedAt = "2024-01-01T00:00:00Z",
            createdAt = "2024-01-01T00:00:00Z",
            updatedAt = "2024-01-01T00:00:00Z",
            metrics = BookMetrics(reads = 2000, likes = 100, ratingAvg = 4.8),
            source = BookSource(
                id = Random.Default.nextInt(100),
                bookId = Random.Default.nextInt(100),
                url = "https://example.com/book.json",
                sizeBytes = 1024000
            )
        )
        
        // When
        val result = bookMapper.toBook(bookDetailResponse)
        
        // Then
        assertEquals("detail123".hashCode(), result.id)
        assertEquals("Detailed Book", result.title)
        assertTrue(result.author is UIText.StringResource)
        assertEquals(R.string.unknown_author, (result.author as UIText.StringResource).resId)
        assertEquals("A detailed description", result.description)
        assertEquals("https://example.com/book.json", result.filePath)
        assertEquals("https://example.com/detail.jpg", result.coverImage?.toString())
        assertEquals(0, result.scrollIndex)
        assertEquals(0, result.scrollOffset)
        assertEquals(0f, result.progress)
        assertNull(result.lastOpened)
    }
    
    @Test
    fun `toBook from BookDetailResponse should handle null source`() = runTest {
        // Given
        val bookDetailResponse = BookDetailResponse(
            id = Random.Default.nextInt(100),
            title = "Detailed Book",
            type = "html",
            description = "A detailed description",
            coverUrl = "https://example.com/detail.jpg",
            language = "en",
            authors = listOf("John Doe"),
            categories = listOf("fantasy"),
            tags = listOf("adventure"),
            publishedAt = "2024-01-01T00:00:00Z",
            createdAt = "2024-01-01T00:00:00Z",
            updatedAt = "2024-01-01T00:00:00Z",
            metrics = BookMetrics(reads = 2000, likes = 100, ratingAvg = 4.8),
            source = null
        )
        
        // When
        val result = bookMapper.toBook(bookDetailResponse)
        
        // Then
        assertEquals("", result.filePath)
    }
    
    @Test
    fun `mapRemoteCategoriesToLocal should map fantasy category correctly`() = runTest {
        // Given
        val remoteCategories = listOf("fantasy")
        
        // When
        val result = bookMapper.toBook(
            BookListResponse(
                id = Random.Default.nextInt(100),
                title = "Test",
                categories = remoteCategories
            )
        )
        
        // Then
        assertEquals(setOf(Category.FANTASY), result.category)
    }
    
    @Test
    fun `mapRemoteCategoriesToLocal should map multiple categories correctly`() = runTest {
        // Given
        val remoteCategories = listOf("fantasy", "romance", "action")
        
        // When
        val result = bookMapper.toBook(
            BookListResponse(
                id = Random.Default.nextInt(100),
                title = "Test",
                categories = remoteCategories
            )
        )
        
        // Then
        assertEquals(setOf(Category.FANTASY, Category.ROMANCE, Category.ACTION), result.category)
    }
    
    @Test
    fun `mapRemoteCategoriesToLocal should use default category for unknown categories`() = runTest {
        // Given
        val remoteCategories = listOf("unknown_category", "another_unknown")
        
        // When
        val result = bookMapper.toBook(
            BookListResponse(
                id = Random.Default.nextInt(100),
                title = "Test",
                categories = remoteCategories
            )
        )
        
        // Then
        assertEquals(DEFAULT_CATEGORY, result.category)
    }
    
    @Test
    fun `mapRemoteCategoriesToLocal should handle empty categories`() = runTest {
        // Given
        val remoteCategories = emptyList<String>()
        
        // When
        val result = bookMapper.toBook(
            BookListResponse(
                id = Random.Default.nextInt(100),
                title = "Test",
                categories = remoteCategories
            )
        )
        
        // Then
        assertEquals(DEFAULT_CATEGORY, result.category)
    }
    
    @Test
    fun `mapRemoteCategoriesToLocal should handle null categories`() = runTest {
        // Given
        val remoteCategories: List<String>? = null
        
        // When
        val result = bookMapper.toBook(
            BookListResponse(
                id = Random.Default.nextInt(100),
                title = "Test",
                categories = remoteCategories
            )
        )
        
        // Then
        assertEquals(DEFAULT_CATEGORY, result.category)
    }
    
    @Test
    fun `mapRemoteCategoriesToLocal should handle mixed known and unknown categories`() = runTest {
        // Given
        val remoteCategories = listOf("fantasy", "unknown_category", "romance")
        
        // When
        val result = bookMapper.toBook(
            BookListResponse(
                id = Random.Default.nextInt(100),
                title = "Test",
                categories = remoteCategories
            )
        )
        
        // Then
        assertEquals(setOf(Category.FANTASY, Category.ROMANCE), result.category)
    }
    
    @Test
    fun `mapRemoteCategoriesToLocal should handle case insensitive mapping`() = runTest {
        // Given
        val remoteCategories = listOf("FANTASY", "Romance", "ACTION")
        
        // When
        val result = bookMapper.toBook(
            BookListResponse(
                id = Random.Default.nextInt(100),
                title = "Test",
                categories = remoteCategories
            )
        )
        
        // Then
        assertEquals(setOf(Category.FANTASY, Category.ROMANCE, Category.ACTION), result.category)
    }
}
