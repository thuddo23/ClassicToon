/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.book_info

import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DriveFileMove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.classictoon.novel.R
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.library.category.Category
import com.classictoon.novel.presentation.core.components.dialog.Dialog
import com.classictoon.novel.presentation.core.components.dialog.SelectableDialogItem
import com.classictoon.novel.ui.book_info.BookInfoEvent

@Composable
fun BookInfoMoveDialog(
    book: Book,
    actionMoveDialog: (BookInfoEvent.OnActionMoveDialog) -> Unit,
    dismissDialog: (BookInfoEvent.OnDismissDialog) -> Unit,
    navigateToHome: () -> Unit
) {
    val context = LocalContext.current

    val categories = remember {
        Category.entries.filter { it !in book.category }
    }
    val selectedCategory = remember {
        mutableStateOf(categories[0])
    }

    Dialog(
        title = stringResource(id = R.string.move_book),
        icon = Icons.AutoMirrored.Outlined.DriveFileMove,
        description = stringResource(
            id = R.string.move_book_description
        ),
        actionEnabled = true,
        onDismiss = { dismissDialog(BookInfoEvent.OnDismissDialog) },
        onAction = {
            actionMoveDialog(
                BookInfoEvent.OnActionMoveDialog(
                    category = selectedCategory.value,
                    context = context,
                    navigateToHome = navigateToHome
                )
            )
        },
        withContent = true,
        items = {
            items(categories, key = { it.name }) {
                val category = when (it) {
                    Category.FANTASY -> stringResource(id = R.string.reading_tab)
                    Category.ROMANCE -> stringResource(id = R.string.already_read_tab)
                    Category.ACTION -> stringResource(id = R.string.planning_tab)
                    Category.THRILLER -> stringResource(id = R.string.dropped_tab)
                    Category.COMEDY -> stringResource(id = R.string.comedy_tab)
                    Category.DRAMA -> stringResource(id = R.string.drama_tab)
                    Category.MYSTERY -> stringResource(id = R.string.mystery_tab)
                    Category.SCIENCE_FICTION -> stringResource(id = R.string.science_fiction_tab)
                    Category.ALL -> stringResource(id = R.string.other_tab)
                }

                SelectableDialogItem(
                    selected = it == selectedCategory.value,
                    title = category
                ) {
                    selectedCategory.value = it
                }
            }
        }
    )
}
