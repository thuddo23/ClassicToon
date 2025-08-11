/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.library

import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoveUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.classictoon.novel.R
import com.classictoon.novel.domain.library.book.SelectableBook
import com.classictoon.novel.domain.library.category.CategoryWithBooks
import com.classictoon.novel.presentation.core.components.dialog.Dialog
import com.classictoon.novel.presentation.core.components.dialog.SelectableDialogItem
import com.classictoon.novel.ui.library.LibraryEvent

@Composable
fun LibraryMoveDialog(
    books: List<SelectableBook>,
    categories: List<CategoryWithBooks>,
    selectedItemsCount: Int,
    actionMoveDialog: (LibraryEvent.OnActionMoveDialog) -> Unit,
    dismissDialog: (LibraryEvent.OnDismissDialog) -> Unit
) {
    val context = LocalContext.current
    val selectedBooks = remember {
        derivedStateOf {
            books.filter { it.selected }
        }
    }
    val moveCategories = remember {
        derivedStateOf {
            categories.mapNotNull { category ->
                if (!selectedBooks.value.all { category.category in it.data.category}) {
                    return@mapNotNull category
                }
                return@mapNotNull null
            }
        }
    }
    val selectedCategory = remember {
        mutableStateOf(moveCategories.value[0])
    }

    Dialog(
        title = stringResource(id = R.string.move_books),
        icon = Icons.Outlined.MoveUp,
        description = stringResource(
            id = R.string.move_books_description,
            selectedItemsCount
        ),
        actionEnabled = true,
        onDismiss = {
            dismissDialog(LibraryEvent.OnDismissDialog)
        },
        onAction = {
            actionMoveDialog(
                LibraryEvent.OnActionMoveDialog(
                    selectedCategory = selectedCategory.value.category,
                    categories = categories,
                    context = context
                )
            )
        },
        withContent = true,
        items = {
            items(moveCategories.value) { category ->
                SelectableDialogItem(
                    selected = category == selectedCategory.value,
                    title = category.title.asString()
                ) {
                    selectedCategory.value = category
                }
            }
        }
    )
}
