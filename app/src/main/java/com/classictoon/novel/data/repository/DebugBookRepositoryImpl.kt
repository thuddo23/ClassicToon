/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.repository

import android.app.Application
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.classictoon.novel.R
import com.classictoon.novel.data.parser.html.HtmlTextParser
import com.classictoon.novel.domain.file.CachedFile
import com.classictoon.novel.domain.file.CachedFileBuilder
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.library.book.BookWithCover
import com.classictoon.novel.domain.library.category.Category
import com.classictoon.novel.domain.library.category.DEFAULT_CATEGORY
import com.classictoon.novel.domain.reader.ReaderText
import com.classictoon.novel.domain.repository.BookRepository
import com.classictoon.novel.domain.ui.UIText
import com.classictoon.novel.domain.util.CoverImage
import java.io.File
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

private const val DEBUG_BOOK_REPO = "DEBUG_BOOK_REPO"

@Singleton
class DebugBookRepositoryImpl @Inject constructor(
    private val application: Application,
    private val bookRepositoryImpl: BookRepositoryImpl,
    private val htmlTextParser: HtmlTextParser
) : BookRepository {

    // Sample HTML book resource IDs - you'll need to create these files
    private val htmlBookResources = listOf(
        R.raw.book_1,
        R.raw.book_2,
        R.raw.book_3,
        R.raw.book_4,
        R.raw.book_5,
        R.raw.book_6,
        R.raw.book_7,
        R.raw.book_8,
        R.raw.book_9,
        R.raw.book_10
    )

    private val debugBooks = listOf(
        Book(
            id = 1,
            title = "The Adventures of Sherlock Holmes",
            author = UIText.StringValue("Arthur Conan Doyle"),
            description = "A collection of twelve short stories featuring the famous detective Sherlock Holmes.",
            filePath = "android.resource://${application.packageName}/${R.raw.book_1}",
            coverImage = null,
            scrollIndex = 0,
            scrollOffset = 0,
            progress = 0f,
            lastOpened = null,
            category = DEFAULT_CATEGORY
        ),
        Book(
            id = 2,
            title = "Pride and Prejudice",
            author = UIText.StringValue("Jane Austen"),
            description = "A romantic novel of manners written by Jane Austen in 1813.",
            filePath = "android.resource://${application.packageName}/${R.raw.book_2}",
            coverImage = null,
            scrollIndex = 0,
            scrollOffset = 0,
            progress = 0f,
            lastOpened = null,
            category = DEFAULT_CATEGORY
        ),
        Book(
            id = 3,
            title = "The Great Gatsby",
            author = UIText.StringValue("F. Scott Fitzgerald"),
            description = "A 1925 novel written by American author F. Scott Fitzgerald.",
            filePath = "android.resource://${application.packageName}/${R.raw.book_3}",
            coverImage = null,
            scrollIndex = 0,
            scrollOffset = 0,
            progress = 0f,
            lastOpened = null,
            category = DEFAULT_CATEGORY
        ),
        Book(
            id = 4,
            title = "To Kill a Mockingbird",
            author = UIText.StringValue("Harper Lee"),
            description = "A novel by Harper Lee published in 1960.",
            filePath = "android.resource://${application.packageName}/${R.raw.book_4}",
            coverImage = null,
            scrollIndex = 0,
            scrollOffset = 0,
            progress = 0f,
            lastOpened = null,
            category = DEFAULT_CATEGORY
        ),
        Book(
            id = 5,
            title = "1984",
            author = UIText.StringValue("George Orwell"),
            description = "A dystopian social science fiction novel by English novelist George Orwell.",
            filePath = "android.resource://${application.packageName}/${R.raw.book_5}",
            coverImage = null,
            scrollIndex = 0,
            scrollOffset = 0,
            progress = 0f,
            lastOpened = null,
            category = DEFAULT_CATEGORY
        ),
        Book(
            id = 6,
            title = "The Catcher in the Rye",
            author = UIText.StringValue("J.D. Salinger"),
            description = "A novel by J. D. Salinger, partially published in serial form in 1945–1946.",
            filePath = "android.resource://${application.packageName}/${R.raw.book_6}",
            coverImage = null,
            scrollIndex = 0,
            scrollOffset = 0,
            progress = 0f,
            lastOpened = null,
            category = DEFAULT_CATEGORY
        ),
        Book(
            id = 7,
            title = "Lord of the Flies",
            author = UIText.StringValue("William Golding"),
            description = "A 1954 novel by Nobel Prize-winning British author William Golding.",
            filePath = "android.resource://${application.packageName}/${R.raw.book_7}",
            coverImage = null,
            scrollIndex = 0,
            scrollOffset = 0,
            progress = 0f,
            lastOpened = null,
            category = DEFAULT_CATEGORY
        ),
        Book(
            id = 8,
            title = "The Hobbit",
            author = UIText.StringValue("J.R.R. Tolkien"),
            description = "A children's fantasy novel by English author J. R. R. Tolkien.",
            filePath = "android.resource://${application.packageName}/${R.raw.book_8}",
            coverImage = null,
            scrollIndex = 0,
            scrollOffset = 0,
            progress = 0f,
            lastOpened = null,
            category = DEFAULT_CATEGORY
        ),
        Book(
            id = 9,
            title = "Fahrenheit 451",
            author = UIText.StringValue("Ray Bradbury"),
            description = "A 1953 dystopian novel by American writer Ray Bradbury.",
            filePath = "android.resource://${application.packageName}/${R.raw.book_9}",
            coverImage = null,
            scrollIndex = 0,
            scrollOffset = 0,
            progress = 0f,
            lastOpened = null,
            category = DEFAULT_CATEGORY
        ),
        Book(
            id = 10,
            title = "Jane Eyre",
            author = UIText.StringValue("Charlotte Brontë"),
            description = "A novel by English writer Charlotte Brontë, published under the pen name Currer Bell.",
            filePath = "android.resource://${application.packageName}/${R.raw.book_10}",
            coverImage = null,
            scrollIndex = 0,
            scrollOffset = 0,
            progress = 0f,
            lastOpened = null,
            category = DEFAULT_CATEGORY
        )
    )

    override suspend fun getBooks(query: String): List<Book> {
        Log.i(DEBUG_BOOK_REPO, "Getting books (normal + debug) with query: \"$query\"")
        
        // Get normal books from the real repository
        val normalBooks = bookRepositoryImpl.getBooks(query)
        
        // Filter debug books based on query
        val filteredDebugBooks = if (query.isBlank()) {
            debugBooks
        } else {
            debugBooks.filter { book ->
                book.title.contains(query, ignoreCase = true) ||
                book.author.asString(application).contains(query, ignoreCase = true) ||
                book.description?.contains(query, ignoreCase = true) == true
            }
        }
        
        // Combine both lists, with debug books having unique IDs starting from 1000
        val adjustedDebugBooks = filteredDebugBooks.map { book ->
            book.copy(id = book.id + 1000) // Ensure unique IDs
        }
        
        return normalBooks + adjustedDebugBooks
    }

    override suspend fun getBooksById(ids: List<Int>): List<Book> {
        Log.i(DEBUG_BOOK_REPO, "Getting books by ids: $ids")
        
        // Separate normal book IDs from debug book IDs
        val normalBookIds = ids.filter { it < 1000 }
        val debugBookIds = ids.filter { it >= 1000 }.map { it - 1000 }
        
        // Get normal books
        val normalBooks = if (normalBookIds.isNotEmpty()) {
            bookRepositoryImpl.getBooksById(normalBookIds)
        } else {
            emptyList()
        }
        
        // Get debug books and adjust their IDs
        val debugBooksResult = debugBooks.filter { it.id in debugBookIds }.map { book ->
            book.copy(id = book.id + 1000)
        }
        
        return normalBooks + debugBooksResult
    }

    override suspend fun getBookText(bookId: Int): List<ReaderText> {
        Log.i(DEBUG_BOOK_REPO, "Loading text for book: $bookId")
        
        // Check if this is a debug book (ID >= 1000)
        if (bookId >= 1000) {
            val debugBookId = bookId - 1000
            val book = debugBooks.find { it.id == debugBookId }
            if (book == null) {
                Log.e(DEBUG_BOOK_REPO, "Debug book with id $debugBookId not found")
                return emptyList()
            }

            return try {
                val resourceId = htmlBookResources.getOrNull(debugBookId - 1)
                if (resourceId == null) {
                    Log.e(DEBUG_BOOK_REPO, "Resource not found for debug book $debugBookId")
                    return emptyList()
                }

                withContext(Dispatchers.IO) {
                    // Create a CachedFile from the resource
                    val cachedFile = createCachedFileFromResource(resourceId, book.title)
                    
                    // Use the existing HtmlTextParser to parse the HTML
                    htmlTextParser.parse(cachedFile)
                }
            } catch (e: Exception) {
                Log.e(DEBUG_BOOK_REPO, "Error loading debug book text for $debugBookId", e)
                emptyList()
            }
        } else {
            // Delegate to normal repository for regular books
            return bookRepositoryImpl.getBookText(bookId)
        }
    }

    /**
     * Creates a CachedFile from a raw resource to work with the existing HtmlTextParser
     */
    private fun createCachedFileFromResource(resourceId: Int, fileName: String): CachedFile {
        return object : CachedFile(
            context = application,
            uri = Uri.EMPTY,
            builder = null,
        ) {
            override val name: String = "$fileName.html"
            override val path: String = "android.resource://${application.packageName}/$resourceId"
            override val isDirectory: Boolean = false
            override val lastModified: Long = 0L
            override val size: Long = 0L

            override fun canAccess(): Boolean = true

            override fun openInputStream(): InputStream? {
                return try {
                    application.resources.openRawResource(resourceId)
                } catch (e: Exception) {
                    Log.e(DEBUG_BOOK_REPO, "Failed to open resource $resourceId", e)
                    null
                }
            }
        }
    }

    override suspend fun insertBook(bookWithCover: BookWithCover) {
        Log.i(DEBUG_BOOK_REPO, "Delegating insertBook to normal repository")
        bookRepositoryImpl.insertBook(bookWithCover)
    }

    override suspend fun updateBook(book: Book) {
        Log.i(DEBUG_BOOK_REPO, "Delegating updateBook to normal repository")
        // Only update if it's not a debug book
        if (book.id < 1000) {
            bookRepositoryImpl.updateBook(book)
        } else {
            Log.w(DEBUG_BOOK_REPO, "Cannot update debug book with id ${book.id}")
        }
    }

    override suspend fun updateCoverImageOfBook(bookWithOldCover: Book, newCoverImage: CoverImage?) {
        Log.i(DEBUG_BOOK_REPO, "Delegating updateCoverImageOfBook to normal repository")
        // Only update if it's not a debug book
        if (bookWithOldCover.id < 1000) {
            bookRepositoryImpl.updateCoverImageOfBook(bookWithOldCover, newCoverImage)
        } else {
            Log.w(DEBUG_BOOK_REPO, "Cannot update cover for debug book with id ${bookWithOldCover.id}")
        }
    }

    override suspend fun deleteBooks(books: List<Book>) {
        Log.i(DEBUG_BOOK_REPO, "Delegating deleteBooks to normal repository")
        // Only delete non-debug books
        val normalBooks = books.filter { it.id < 1000 }
        if (normalBooks.isNotEmpty()) {
            bookRepositoryImpl.deleteBooks(normalBooks)
        }
        
        val debugBooks = books.filter { it.id >= 1000 }
        if (debugBooks.isNotEmpty()) {
            Log.w(DEBUG_BOOK_REPO, "Cannot delete debug books: ${debugBooks.map { it.id }}")
        }
    }

    override suspend fun canResetCover(bookId: Int): Boolean {
        return if (bookId < 1000) {
            bookRepositoryImpl.canResetCover(bookId)
        } else {
            false // Debug books cannot reset cover
        }
    }

    override suspend fun resetCoverImage(bookId: Int): Boolean {
        return if (bookId < 1000) {
            bookRepositoryImpl.resetCoverImage(bookId)
        } else {
            false // Debug books cannot reset cover
        }
    }
}