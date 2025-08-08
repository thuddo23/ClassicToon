/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.browse

import androidx.compose.runtime.Immutable
import com.classictoon.novel.domain.browse.file.SelectableFile
import com.classictoon.novel.domain.library.book.SelectableNullableBook
import com.classictoon.novel.domain.util.BottomSheet
import com.classictoon.novel.domain.util.Dialog

@Immutable
data class BrowseState(
    val files: List<SelectableFile> = emptyList(),

    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,

    val selectedItemsCount: Int = 0,
    val hasSelectedItems: Boolean = false,

    val showSearch: Boolean = false,
    val searchQuery: String = "",
    val hasFocused: Boolean = false,

    val dialog: Dialog? = null,
    val bottomSheet: BottomSheet? = null,

    val selectedBooksAddDialog: List<SelectableNullableBook> = emptyList(),
    val loadingAddDialog: Boolean = false
)
