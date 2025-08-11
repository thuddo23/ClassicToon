package com.classictoon.novel.ui.home

import com.classictoon.novel.domain.library.book.Book

data class ServerBooksUiState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val hasMorePages: Boolean = true
)