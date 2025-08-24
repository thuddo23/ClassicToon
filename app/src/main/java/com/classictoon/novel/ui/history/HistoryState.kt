/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.history

import androidx.compose.runtime.Immutable
import com.classictoon.novel.domain.history.GroupedHistory
import com.classictoon.novel.domain.util.Dialog

@Immutable
data class HistoryState(
    val history: List<GroupedHistory> = emptyList(),

    val isRefreshing: Boolean = false,
    val isLoading: Boolean = true,

    val showSearch: Boolean = false,
    val searchQuery: String = "",
    val hasFocused: Boolean = false,

    val dialog: Dialog? = null
)
