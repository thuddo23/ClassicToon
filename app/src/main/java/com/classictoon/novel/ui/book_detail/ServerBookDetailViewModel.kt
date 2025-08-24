/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.book_detail

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.library.book.BookWithCover
import com.classictoon.novel.domain.library.category.Category
import com.classictoon.novel.domain.use_case.GetServerBookByIdUseCase
import com.classictoon.novel.domain.use_case.GetServerBookContentUseCase
import com.classictoon.novel.domain.use_case.book.InsertBook
import com.classictoon.novel.domain.ui.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ServerBookDetailViewModel @Inject constructor(
    private val getServerBookByIdUseCase: GetServerBookByIdUseCase,
    private val getServerBookContentUseCase: GetServerBookContentUseCase,
    private val insertBook: InsertBook,
    private val context: Application,
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ServerBookDetailUiState())
    val uiState: StateFlow<ServerBookDetailUiState> = _uiState.asStateFlow()
    
    fun loadBook(bookId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val book = getServerBookByIdUseCase(bookId)
                if (book != null) {
                    _uiState.value = _uiState.value.copy(
                        book = book,
                        isLoading = false
                    )
                    
                    // Auto-download book content after loading book details
                    autoDownloadBookContent(bookId, book)
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = "Book not found",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to load book",
                    isLoading = false
                )
            }
        }
    }
    
    private fun autoDownloadBookContent(bookId: Int, serverBook: Book) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isDownloadingContent = true, error = null)
            
            try {
                // Get the book content from the server
                val content = getServerBookContentUseCase(bookId)
                // TODO update save real file here
                // Save to internal storage with unique filename
                val fileName = "book_${bookId}.html"
                val rootDir = context.filesDir.path
                val booksDir = File("${rootDir}/books")
                val file = File(booksDir, fileName)
                
                // Ensure directory exists
                if (!booksDir.exists()) {
                    val created = booksDir.mkdirs()
                    android.util.Log.d("ServerBookDetailViewModel", "Created books directory: $created, Path: ${booksDir.absolutePath}")
                }
                
                // Write content to file
                file.writeText(content)
                android.util.Log.d("ServerBookDetailViewModel", "Successfully wrote book content to: ${file.absolutePath}")
                android.util.Log.d("ServerBookDetailViewModel", "File exists: ${file.exists()}, Can read: ${file.canRead()}, Size: ${file.length()}")
                
                // Create a Book entity for Room database
                val bookForRoom = Book(
                    id = bookId, // Room will auto-generate the ID
                    title = serverBook.title,
                    author = serverBook.author,
                    description = serverBook.description,
                    filePath = file.absolutePath,
                    coverImage = serverBook.coverImage,
                    scrollIndex = 0,
                    scrollOffset = 0,
                    progress = 0f,
                    lastOpened = System.currentTimeMillis(),
                    category = serverBook.category
                )
                
                // Save book to Room database
                insertBook.execute(BookWithCover(
                    book = bookForRoom,
                    coverImage = null // No cover image for now, can be updated later
                ))
                
                _uiState.value = _uiState.value.copy(
                    isDownloadingContent = false,
                    contentDownloadSuccess = true,
                    savedBook = bookForRoom,
                    savedFileName = fileName
                )
            } catch (e: Exception) {
                android.util.Log.e("ServerBookDetailViewModel", "Error downloading book content for book $bookId", e)
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to download book content",
                    isDownloadingContent = false
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class ServerBookDetailUiState(
    val book: Book? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isDownloadingContent: Boolean = false,
    val contentDownloadSuccess: Boolean = false,
    val savedBook: Book? = null,
    val savedFileName: String? = null
)
