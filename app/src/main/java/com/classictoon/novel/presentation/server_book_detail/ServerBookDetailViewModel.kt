/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.server_book_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.classictoon.novel.data.local.dto.BookEntity
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.library.book.BookWithCover
import com.classictoon.novel.domain.use_case.GetServerBookByIdUseCase
import com.classictoon.novel.domain.use_case.GetServerBookContentUseCase
import com.classictoon.novel.domain.use_case.DownloadBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServerBookDetailViewModel @Inject constructor(
    private val getServerBookByIdUseCase: GetServerBookByIdUseCase,
    private val getServerBookContentUseCase: GetServerBookContentUseCase,
    private val downloadBookUseCase: DownloadBookUseCase
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
    
    fun getBookContent(bookId: Int, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val content = getServerBookContentUseCase(bookId)
                onSuccess(content)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to load book content"
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun downloadBook(bookId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isDownloading = true, error = null)
            
            try {
                val result = downloadBookUseCase.execute(bookId)
                result.fold(
                    onSuccess = { book ->
                        _uiState.value = _uiState.value.copy(
                            isDownloading = false,
                            downloadSuccess = true,
                            downloadedBook = book
                        )
                    },
                    onFailure = { exception ->
                        _uiState.value = _uiState.value.copy(
                            error = exception.message ?: "Failed to download book",
                            isDownloading = false
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to download book",
                    isDownloading = false
                )
            }
        }
    }
}

data class ServerBookDetailUiState(
    val book: Book? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isDownloading: Boolean = false,
    val downloadSuccess: Boolean = false,
    val downloadedBook: Book? = null
)
