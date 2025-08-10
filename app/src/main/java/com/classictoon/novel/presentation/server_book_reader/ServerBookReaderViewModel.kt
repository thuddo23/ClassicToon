/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.server_book_reader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.classictoon.novel.domain.use_case.GetServerBookContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServerBookReaderViewModel @Inject constructor(
    private val getServerBookContentUseCase: GetServerBookContentUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ServerBookReaderUiState())
    val uiState: StateFlow<ServerBookReaderUiState> = _uiState.asStateFlow()
    
    fun loadBookContent(bookId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val content = getServerBookContentUseCase(bookId)
                _uiState.value = _uiState.value.copy(
                    content = content,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to load book content",
                    isLoading = false
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class ServerBookReaderUiState(
    val content: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
