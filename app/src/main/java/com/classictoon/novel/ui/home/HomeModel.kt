/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.use_case.GetServerBooksUseCase
import com.classictoon.novel.domain.use_case.GetServerBookByIdUseCase
import com.classictoon.novel.domain.use_case.GetServerBookContentUseCase
import com.classictoon.novel.domain.use_case.GetTrendingBooksUseCase
import com.classictoon.novel.domain.use_case.GetTopPicksUseCase
import com.classictoon.novel.domain.use_case.GetNewestBooksUseCase
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
    private val getServerBookContentUseCase: GetServerBookContentUseCase,
    private val getTrendingBooksUseCase: GetTrendingBooksUseCase,
    private val getTopPicksUseCase: GetTopPicksUseCase,
    private val getNewestBooksUseCase: GetNewestBooksUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadHomeFeed()
    }
    
    fun loadHomeFeed() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                // Load all feed sections in parallel
                val trendingBooks = getTrendingBooksUseCase(10)
                val topPicks = getTopPicksUseCase(10)
                val newestBooks = getNewestBooksUseCase(10)
                
                _uiState.value = _uiState.value.copy(
                    trendingBooks = trendingBooks,
                    topPicks = topPicks,
                    newestBooks = newestBooks,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to load home feed",
                    isLoading = false
                )
            }
        }
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
