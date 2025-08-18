/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.server_book_detail

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.use_case.GetServerBookByIdUseCase
import com.classictoon.novel.domain.use_case.GetServerBookContentUseCase
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
                    autoDownloadBookContent(bookId, book.title)
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
    
    private fun autoDownloadBookContent(bookId: Int, bookTitle: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isDownloadingContent = true, error = null)
            
            try {
                // Get the book content from the server
                val content = getServerBookContentUseCase(bookId)
                // TODO update save real file here
                // Save to internal storage
                val fileName = "sample_book.html"
                val rootDir = context.filesDir.path
                val file = File("${rootDir}/books/$fileName")
                
                // Ensure directory exists
                file.parentFile?.mkdirs()
                
                // Write content to file
                file.writeText(content)
                
                _uiState.value = _uiState.value.copy(
                    isDownloadingContent = false,
                    contentDownloadSuccess = true
                )
            } catch (e: Exception) {
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
    val contentDownloadSuccess: Boolean = false
)
