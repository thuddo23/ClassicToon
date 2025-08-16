/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclarite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.remote

import com.classictoon.novel.data.remote.dto.*
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceTest {
    
    private lateinit var apiService: ApiService
    
    @Before
    fun setUp() {
        // Create HTTP client with logging for debugging
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        
        // Create Retrofit instance pointing to localhost:8000
        apiService = Retrofit.Builder()
            .baseUrl("http://localhost:8000/api/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    
    @Test
    fun `getTrendingBooks should return list of books`() = runTest {
        // When
        val result = apiService.getTrendingBooks(limit = 5)
        
        // Then
        assertNotNull(result)
        assertTrue("Should return a list of books", result.isNotEmpty())
        
        // Verify the structure of the first book
        val firstBook = result.first()
        assertNotNull("Book ID should not be null", firstBook.id)
        assertNotNull("Book title should not be null", firstBook.title)
        assertNotNull("Book type should not be null", firstBook.type)
        
        // Log the results for debugging
        println("Retrieved ${result.size} trending books:")
        result.forEach { book ->
            println("- ${book.title} (${book.id}) - ${book.type}")
        }
    }
    
    @Test
    fun `getTopPicks should return top picks response`() = runTest {
        // When
        val result = apiService.getTopPicks(limit = 3)
        
        // Then
        assertNotNull(result)
        assertNotNull("Items should not be null", result.items)
        assertTrue("Should return top picks", result.items.isNotEmpty())
        
        // Verify the structure of the first top pick
        val firstPick = result.items.first()
        assertNotNull("Book should not be null", firstPick.book)
        assertNotNull("Book ID should not be null", firstPick.book.id)
        assertNotNull("Book title should not be null", firstPick.book.title)
        assertTrue("Rank should be positive", firstPick.rank > 0)
        
        // Log the results for debugging
        println("Retrieved ${result.items.size} top picks:")
        result.items.forEach { pick ->
            println("- Rank ${pick.rank}: ${pick.book.title} (${pick.book.id}) - ${pick.note}")
        }
    }
    
    @Test
    fun `getCategoryRanking should return ranking response`() = runTest {
        // When
        val result = apiService.getCategoryRanking(category = "fantasy", period = "weekly")
        
        // Then
        assertNotNull(result)
        assertEquals("fantasy", result.category)
        assertEquals("weekly", result.period)
        assertNotNull("Generated at should not be null", result.generatedAt)
        assertNotNull("Items should not be null", result.items)
        assertTrue("Should return ranked books", result.items.isNotEmpty())
        
        // Verify the structure of the first ranked book
        val firstRanked = result.items.first()
        assertNotNull("Book should not be null", firstRanked.book)
        assertNotNull("Book ID should not be null", firstRanked.book.id)
        assertNotNull("Book title should not be null", firstRanked.book.title)
        assertTrue("Score should be positive", firstRanked.score > 0)
        
        // Log the results for debugging
        println("Retrieved ${result.items.size} ranked books for category '${result.category}':")
        result.items.take(5).forEach { ranked ->
            println("- Score ${ranked.score}: ${ranked.book.title} (${ranked.book.id})")
        }
    }
    
    @Test
    fun `getNewestBooks should return list of newest books`() = runTest {
        // When
        val result = apiService.getNewestBooks(limit = 5)
        
        // Then
        assertNotNull(result)
        assertTrue("Should return newest books", result.isNotEmpty())
        
        // Verify the structure of the first book
        val firstBook = result.first()
        assertNotNull("Book ID should not be null", firstBook.id)
        assertNotNull("Book title should not be null", firstBook.title)
        assertNotNull("Book type should not be null", firstBook.type)
        
        // Log the results for debugging
        println("Retrieved ${result.size} newest books:")
        result.forEach { book ->
            println("- ${book.title} (${book.id}) - ${book.type} - Published: ${book.publishedAt}")
        }
    }
    
    @Test
    fun `getBooks should return search response with pagination`() = runTest {
        // When
        val result = apiService.getBooks(
            page = 1,
            limit = 10,
            searchQuery = "fantasy",
            category = "fantasy",
            type = "html",
            sort = "trending"
        )
        
        // Then
        assertNotNull(result)
        assertNotNull("Items should not be null", result.items)
        assertTrue("Page should be positive", result.page > 0)
        assertTrue("Total should be non-negative", result.total >= 0)
        
        // Verify the structure of the first book if available
        if (result.items.isNotEmpty()) {
            val firstBook = result.items.first()
            assertNotNull("Book ID should not be null", firstBook.id)
            assertNotNull("Book title should not be null", firstBook.title)
            assertNotNull("Book type should not be null", firstBook.type)
        }
        
        // Log the results for debugging
        println("Retrieved ${result.items.size} books (page ${result.page} of ${result.total}):")
        result.items.forEach { book ->
            println("- ${book.title} (${book.id}) - ${book.type}")
        }
    }
    
    @Test
    fun `getBookDetail should return book detail response`() = runTest {
        // First get a list of books to find a valid book ID
        val books = apiService.getTrendingBooks(limit = 1)
        assertTrue("Should have at least one book to test with", books.isNotEmpty())
        
        val bookId = books.first().id.hashCode() // Convert string ID to int
        
        // When
        val result = apiService.getBookDetail(bookId = bookId)
        
        // Then
        assertNotNull(result)
        assertEquals(books.first().id, result.id)
        assertNotNull("Book title should not be null", result.title)
        assertNotNull("Book type should not be null", result.type)
        assertNotNull("Book description should not be null", result.description)
        assertNotNull("Book language should not be null", result.language)
        assertNotNull("Book authors should not be null", result.authors)
        assertNotNull("Book categories should not be null", result.categories)
        assertNotNull("Book tags should not be null", result.tags)
        assertNotNull("Book publishedAt should not be null", result.publishedAt)
        assertNotNull("Book createdAt should not be null", result.createdAt)
        assertNotNull("Book updatedAt should not be null", result.updatedAt)
        assertNotNull("Book metrics should not be null", result.metrics)
        
        // Log the results for debugging
        println("Retrieved book detail for '${result.title}' (${result.id}):")
        println("- Type: ${result.type}")
        println("- Language: ${result.language}")
        println("- Authors: ${result.authors}")
        println("- Categories: ${result.categories}")
        println("- Tags: ${result.tags}")
        println("- Metrics: ${result.metrics.reads} reads, ${result.metrics.likes} likes, ${result.metrics.ratingAvg} rating")
        println("- Source: ${result.source?.url}")
    }
    
    @Test
    fun `getBookContent should return book content string`() = runTest {
        // First get a list of books to find a valid book ID
        val books = apiService.getTrendingBooks(limit = 1)
        assertTrue("Should have at least one book to test with", books.isNotEmpty())
        
        val bookId = books.first().id.hashCode() // Convert string ID to int
        
        // When
        val result = apiService.getBookContent(bookId = bookId)
        
        // Then
        assertNotNull(result)
        assertTrue("Content should not be empty", result.isNotEmpty())
        assertTrue("Content should contain HTML", result.contains("<") || result.contains(">"))
        
        // Log the results for debugging
        println("Retrieved book content for '${books.first().title}' (${books.first().id}):")
        println("Content length: ${result.length} characters")
        println("Content preview: ${result.take(200)}...")
    }
    
    @Test
    fun `getTrendingBooks with default limit should use default value`() = runTest {
        // When
        val result = apiService.getTrendingBooks()
        
        // Then
        assertNotNull(result)
        assertTrue("Should return books with default limit", result.isNotEmpty())
        
        // Log the results for debugging
        println("Retrieved ${result.size} trending books with default limit")
    }
    
    @Test
    fun `getCategoryRanking with default period should use default value`() = runTest {
        // When
        val result = apiService.getCategoryRanking(category = "fantasy")
        
        // Then
        assertNotNull(result)
        assertEquals("fantasy", result.category)
        assertEquals("all_time", result.period) // Default period
        assertNotNull("Items should not be null", result.items)
        
        // Log the results for debugging
        println("Retrieved ${result.items.size} ranked books for category '${result.category}' with default period '${result.period}'")
    }
    
    @Test
    fun `getBooks should work with different search queries`() = runTest {
        // Test different search scenarios
        val searchQueries = listOf("romance", "mystery", "adventure", "thriller")
        
        searchQueries.forEach { query ->
            // When
            val result = apiService.getBooks(
                page = 1,
                limit = 5,
                searchQuery = query
            )
            
            // Then
            assertNotNull("Result should not be null for query '$query'", result)
            assertNotNull("Items should not be null for query '$query'", result.items)
            
            // Log the results for debugging
            println("Search query '$query' returned ${result.items.size} books (page ${result.page} of ${result.total})")
        }
    }
    
    @Test
    fun `getBooks should work with different categories`() = runTest {
        // Test different categories
        val categories = listOf("fantasy", "romance", "mystery", "thriller", "comedy")
        
        categories.forEach { category ->
            // When
            val result = apiService.getBooks(
                page = 1,
                limit = 5,
                category = category
            )
            
            // Then
            assertNotNull("Result should not be null for category '$category'", result)
            assertNotNull("Items should not be null for category '$category'", result.items)
            
            // Log the results for debugging
            println("Category '$category' returned ${result.items.size} books (page ${result.page} of ${result.total})")
        }
    }
    
    @Test
    fun `getBooks should work with different sort options`() = runTest {
        // Test different sort options
        val sortOptions = listOf("trending", "newest", "rating")
        
        sortOptions.forEach { sort ->
            // When
            val result = apiService.getBooks(
                page = 1,
                limit = 5,
                sort = sort
            )
            
            // Then
            assertNotNull("Result should not be null for sort '$sort'", result)
            assertNotNull("Items should not be null for sort '$sort'", result.items)
            
            // Log the results for debugging
            println("Sort '$sort' returned ${result.items.size} books (page ${result.page} of ${result.total})")
        }
    }
    
    @Test
    fun `getBooks should work with pagination`() = runTest {
        // Test pagination
        val pages = listOf(1, 2, 3)
        
        pages.forEach { page ->
            // When
            val result = apiService.getBooks(
                page = page,
                limit = 3
            )
            
            // Then
            assertNotNull("Result should not be null for page $page", result)
            assertNotNull("Items should not be null for page $page", result.items)
            assertEquals("Page should match requested page", page, result.page)
            
            // Log the results for debugging
            println("Page $page returned ${result.items.size} books (total: ${result.total})")
        }
    }
    
    @Test
    fun `getCategoryRanking should work with different periods`() = runTest {
        // Test different ranking periods
        val periods = listOf("weekly", "monthly", "all_time")
        
        periods.forEach { period ->
            // When
            val result = apiService.getCategoryRanking(category = "fantasy", period = period)
            
            // Then
            assertNotNull("Result should not be null for period '$period'", result)
            assertEquals("Category should match", "fantasy", result.category)
            assertEquals("Period should match", period, result.period)
            assertNotNull("Items should not be null for period '$period'", result.items)
            
            // Log the results for debugging
            println("Period '$period' returned ${result.items.size} ranked books for category '${result.category}'")
        }
    }
    
    @Test
    fun `getCategoryRanking should work with different categories`() = runTest {
        // Test different categories for ranking
        val categories = listOf("fantasy", "romance", "mystery", "thriller")
        
        categories.forEach { category ->
            // When
            val result = apiService.getCategoryRanking(category = category)
            
            // Then
            assertNotNull("Result should not be null for category '$category'", result)
            assertEquals("Category should match", category, result.category)
            assertNotNull("Items should not be null for category '$category'", result.items)
            
            // Log the results for debugging
            println("Category '$category' ranking returned ${result.items.size} books")
        }
    }
}
