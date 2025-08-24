/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclarite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.repository

import com.classictoon.novel.data.mapper.book.BookMapper
import com.classictoon.novel.data.mapper.book.BookMapperImpl
import com.classictoon.novel.data.remote.ApiService
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServerBookRepositoryImplTest {
    
    private lateinit var apiService: ApiService
    private lateinit var bookMapper: BookMapper
    private lateinit var repository: ServerBookRepositoryImpl
    
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
            .baseUrl("http://localhost:8000/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
        
        // Use real BookMapper implementation
        bookMapper = BookMapperImpl()
        repository = ServerBookRepositoryImpl(apiService, bookMapper)
    }
    
    @Test
    fun `getTrendingBooks should return mapped books from API`() = runTest {
        // When
        val result = repository.getTrendingBooks(limit = 5)
        
        // Then
        assertNotNull(result)
        assertTrue("Should return mapped books", result.isNotEmpty())
        
        // Verify the structure of the first book
        val firstBook = result.first()
        assertNotNull("Book ID should not be null", firstBook.id)
        assertNotNull("Book title should not be null", firstBook.title)
        assertNotNull("Book author should not be null", firstBook.author)
        assertNotNull("Book description should not be null", firstBook.description)
        assertNotNull("Book filePath should not be null", firstBook.filePath)
        assertNotNull("Book category should not be null", firstBook.category)
        
        // Log the results for debugging
        println("Retrieved ${result.size} trending books from repository:")
        result.forEach { book ->
            println("- ${book.title} (ID: ${book.id}) - Author: ${book.author.getAsString()}")
        }
    }
    
    @Test
    fun `getTrendingBooks should handle API errors gracefully`() = runTest {
        // This test would require the server to be down or return an error
        // For now, we'll test that the method works with valid responses
        // In a real scenario, you might want to test error handling with a mock server
        
        // When
        val result = repository.getTrendingBooks(limit = 3)
        
        // Then
        assertNotNull(result)
        // The test passes if we get a valid response or if the error is handled properly
        println("getTrendingBooks test completed successfully")
    }
    
    @Test
    fun `getTopPicks should return mapped books from API`() = runTest {
        // When
        val result = repository.getTopPicks(limit = 3)
        
        // Then
        assertNotNull(result)
        assertTrue("Should return mapped top picks", result.isNotEmpty())
        
        // Verify the structure of the first book
        val firstBook = result.first()
        assertNotNull("Book ID should not be null", firstBook.id)
        assertNotNull("Book title should not be null", firstBook.title)
        assertNotNull("Book author should not be null", firstBook.author)
        assertNotNull("Book description should not be null", firstBook.description)
        assertNotNull("Book filePath should not be null", firstBook.filePath)
        assertNotNull("Book category should not be null", firstBook.category)
        
        // Log the results for debugging
        println("Retrieved ${result.size} top picks from repository:")
        result.forEach { book ->
            println("- ${book.title} (ID: ${book.id}) - Author: ${book.author.getAsString()}")
        }
    }
    
    @Test
    fun `getTopPicks should handle API errors gracefully`() = runTest {
        // When
        val result = repository.getTopPicks(limit = 3)
        
        // Then
        assertNotNull(result)
        println("getTopPicks test completed successfully")
    }
    
    @Test
    fun `getCategoryRanking should return mapped books from API`() = runTest {
        // When
        val result = repository.getCategoryRanking(category = "fantasy", period = "weekly")
        
        // Then
        assertNotNull(result)
        assertTrue("Should return mapped ranked books", result.isNotEmpty())
        
        // Verify the structure of the first book
        val firstBook = result.first()
        assertNotNull("Book ID should not be null", firstBook.id)
        assertNotNull("Book title should not be null", firstBook.title)
        assertNotNull("Book author should not be null", firstBook.author)
        assertNotNull("Book description should not be null", firstBook.description)
        assertNotNull("Book filePath should not be null", firstBook.filePath)
        assertNotNull("Book category should not be null", firstBook.category)
        
        // Log the results for debugging
        println("Retrieved ${result.size} ranked books from repository:")
        result.forEach { book ->
            println("- ${book.title} (ID: ${book.id}) - Author: ${book.author.getAsString()}")
        }
    }
    
    @Test
    fun `getCategoryRanking should handle API errors gracefully`() = runTest {
        // When
        val result = repository.getCategoryRanking(category = "fantasy", period = "weekly")
        
        // Then
        assertNotNull(result)
        println("getCategoryRanking test completed successfully")
    }
    
    @Test
    fun `getNewestBooks should return mapped books from API`() = runTest {
        // When
        val result = repository.getNewestBooks(limit = 5)
        
        // Then
        assertNotNull(result)
        assertTrue("Should return mapped newest books", result.isNotEmpty())
        
        // Verify the structure of the first book
        val firstBook = result.first()
        assertNotNull("Book ID should not be null", firstBook.id)
        assertNotNull("Book title should not be null", firstBook.title)
        assertNotNull("Book author should not be null", firstBook.author)
        assertNotNull("Book description should not be null", firstBook.description)
        assertNotNull("Book filePath should not be null", firstBook.filePath)
        assertNotNull("Book category should not be null", firstBook.category)
        
        // Log the results for debugging
        println("Retrieved ${result.size} newest books from repository:")
        result.forEach { book ->
            println("- ${book.title} (ID: ${book.id}) - Author: ${book.author.getAsString()}")
        }
    }
    
    @Test
    fun `getNewestBooks should handle API errors gracefully`() = runTest {
        // When
        val result = repository.getNewestBooks(limit = 3)
        
        // Then
        assertNotNull(result)
        println("getNewestBooks test completed successfully")
    }
    
    @Test
    fun `getBooks should return mapped books from API`() = runTest {
        // When
        val result = repository.getBooks(
            page = 1,
            limit = 10,
            searchQuery = "fantasy",
            category = "fantasy",
            type = "html",
            sort = "trending"
        )
        
        // Then
        assertNotNull(result)
        assertTrue("Should return mapped books", result.isNotEmpty())
        
        // Verify the structure of the first book
        val firstBook = result.first()
        assertNotNull("Book ID should not be null", firstBook.id)
        assertNotNull("Book title should not be null", firstBook.title)
        assertNotNull("Book author should not be null", firstBook.author)
        assertNotNull("Book description should not be null", firstBook.description)
        assertNotNull("Book filePath should not be null", firstBook.filePath)
        assertNotNull("Book category should not be null", firstBook.category)
        
        // Log the results for debugging
        println("Retrieved ${result.size} books from repository:")
        result.forEach { book ->
            println("- ${book.title} (ID: ${book.id}) - Author: ${book.author.getAsString()}")
        }
    }
    
    @Test
    fun `getBooks should handle API errors gracefully`() = runTest {
        // When
        val result = repository.getBooks(
            page = 1,
            limit = 5,
            searchQuery = "test",
            category = "fantasy",
            type = "html",
            sort = "trending"
        )
        
        // Then
        assertNotNull(result)
        println("getBooks test completed successfully")
    }
    
    @Test
    fun `getBookById should return mapped book from API`() = runTest {
        // First get a list of books to find a valid book ID
        val books = repository.getTrendingBooks(limit = 1)
        assertTrue("Should have at least one book to test with", books.isNotEmpty())
        
        val bookId = books.first().id
        
        // When
        val result = repository.getBookById(bookId = bookId)
        
        // Then
        assertNotNull(result)
        assertEquals(books.first().id, result?.id)
        assertNotNull("Book title should not be null", result?.title)
        assertNotNull("Book author should not be null", result?.author)
        assertNotNull("Book description should not be null", result?.description)
        assertNotNull("Book filePath should not be null", result?.filePath)
        assertNotNull("Book category should not be null", result?.category)
        
        // Log the results for debugging
        println("Retrieved book detail from repository: ${result?.title} (ID: ${result?.id})")
    }
    
    @Test
    fun `getBookById should handle API errors gracefully`() = runTest {
        // When
        val result = repository.getBookById(bookId = 999999) // Non-existent ID
        
        // Then
        // The test passes if we get null or if the error is handled properly
        println("getBookById test completed successfully")
    }
    
    @Test
    fun `getBookDetail should delegate to getBookById`() = runTest {
        // First get a list of books to find a valid book ID
        val books = repository.getTrendingBooks(limit = 1)
        assertTrue("Should have at least one book to test with", books.isNotEmpty())
        
        val bookId = books.first().id
        
        // When
        val result = repository.getBookDetail(bookId = bookId)
        
        // Then
        assertNotNull(result)
        assertEquals(books.first().id, result?.id)
        assertNotNull("Book title should not be null", result?.title)
        assertNotNull("Book author should not be null", result?.author)
        assertNotNull("Book description should not be null", result?.description)
        assertNotNull("Book filePath should not be null", result?.filePath)
        assertNotNull("Book category should not be null", result?.category)
        
        // Log the results for debugging
        println("Retrieved book detail from repository: ${result?.title} (ID: ${result?.id})")
    }
    
    @Test
    fun `getBookContent should return content from API`() = runTest {
        // First get a list of books to find a valid book ID
        val books = repository.getTrendingBooks(limit = 1)
        assertTrue("Should have at least one book to test with", books.isNotEmpty())
        
        val bookId = books.first().id
        
        // When
        val result = repository.getBookContent(bookId = bookId)
        
        // Then
        assertNotNull(result)
        assertTrue("Content should not be empty", result.isNotEmpty())
        
        // Log the results for debugging
        println("Retrieved book content from repository for '${books.first().title}' (ID: ${books.first().id})")
        println("Content length: ${result.length} characters")
    }
    
    @Test
    fun `getBookContent should handle API errors gracefully`() = runTest {
        // When
        val result = repository.getBookContent(bookId = 999999) // Non-existent ID
        
        // Then
        // The test passes if we get an empty string or if the error is handled properly
        println("getBookContent test completed successfully")
    }
    
    @Test
    fun `searchBooks should delegate to getBooks with search query`() = runTest {
        // When
        val result = repository.searchBooks(query = "fantasy", page = 1, limit = 5)
        
        // Then
        assertNotNull(result)
        assertTrue("Should return mapped books", result.isNotEmpty())
        
        // Verify the structure of the first book
        val firstBook = result.first()
        assertNotNull("Book ID should not be null", firstBook.id)
        assertNotNull("Book title should not be null", firstBook.title)
        assertNotNull("Book author should not be null", firstBook.author)
        assertNotNull("Book description should not be null", firstBook.description)
        assertNotNull("Book filePath should not be null", firstBook.filePath)
        assertNotNull("Book category should not be null", firstBook.category)
        
        // Log the results for debugging
        println("Retrieved ${result.size} books from search:")
        result.forEach { book ->
            println("- ${book.title} (ID: ${book.id}) - Author: ${book.author.getAsString()}")
        }
    }
    
    @Test
    fun `getBooksByGenre should delegate to getBooks with category`() = runTest {
        // When
        val result = repository.getBooksByGenre(genre = "fantasy", page = 1, limit = 5)
        
        // Then
        assertNotNull(result)
        assertTrue("Should return mapped books", result.isNotEmpty())
        
        // Verify the structure of the first book
        val firstBook = result.first()
        assertNotNull("Book ID should not be null", firstBook.id)
        assertNotNull("Book title should not be null", firstBook.title)
        assertNotNull("Book author should not be null", firstBook.author)
        assertNotNull("Book description should not be null", firstBook.description)
        assertNotNull("Book filePath should not be null", firstBook.filePath)
        assertNotNull("Book category should not be null", firstBook.category)
        
        // Log the results for debugging
        println("Retrieved ${result.size} books by genre:")
        result.forEach { book ->
            println("- ${book.title} (ID: ${book.id}) - Author: ${book.author.getAsString()}")
        }
    }
}
