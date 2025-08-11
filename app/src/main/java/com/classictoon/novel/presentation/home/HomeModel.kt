/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.use_case.GetServerBooksUseCase
import com.classictoon.novel.domain.use_case.GetServerBookByIdUseCase
import com.classictoon.novel.domain.use_case.GetServerBookContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeModel @Inject constructor(
    private val getServerBooksUseCase: GetServerBooksUseCase,
    private val getServerBookByIdUseCase: GetServerBookByIdUseCase,
    private val getServerBookContentUseCase: GetServerBookContentUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ServerBooksUiState())
    val uiState: StateFlow<ServerBooksUiState> = _uiState.asStateFlow()
    
    init {
        loadBooks()
    }
    
    fun loadBooks(
        page: Int = 1,
        searchQuery: String? = null,
        genre: String? = null
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val books = getServerBooksUseCase(page, 100, searchQuery, genre)
                _uiState.value = _uiState.value.copy(
                    books = _uiState.value.books + books,
                    isLoading = false,
                    currentPage = page
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Unknown error occurred",
                    isLoading = false
                )
            }
        }
    }
    
    fun searchBooks(query: String) {
        if (query.isBlank()) {
            loadBooks()
        } else {
            loadBooks(searchQuery = query)
        }
    }
    
    fun filterByGenre(genre: String?) {
        loadBooks(genre = genre)
    }

    fun loadMoreBooks() {
        val nextPage = _uiState.value.currentPage + 1
        loadBooks(nextPage)
    }
    
    fun getBookById(bookId: Int, onSuccess: (Book) -> Unit) {
        viewModelScope.launch {
            try {
                val book = getServerBookByIdUseCase(bookId)
                book?.let { onSuccess(it) }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to load book details"
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
}

data class ServerBooksUiState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val hasMorePages: Boolean = true
)
