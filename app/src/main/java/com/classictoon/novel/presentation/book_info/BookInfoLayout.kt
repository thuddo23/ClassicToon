/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.book_info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.presentation.core.components.common.LazyColumnWithScrollbar
import com.classictoon.novel.presentation.core.constants.providePrimaryScrollbar
import com.classictoon.novel.ui.book_info.BookInfoEvent

@Composable
fun BookInfoLayout(
    book: Book,
    listState: LazyListState,
    paddingValues: PaddingValues,
    showChangeCoverBottomSheet: (BookInfoEvent.OnShowChangeCoverBottomSheet) -> Unit,
    showTitleDialog: (BookInfoEvent.OnShowTitleDialog) -> Unit,
    showAuthorDialog: (BookInfoEvent.OnShowAuthorDialog) -> Unit,
    showDescriptionDialog: (BookInfoEvent.OnShowDescriptionDialog) -> Unit,
    showMoveDialog: (BookInfoEvent.OnShowMoveDialog) -> Unit,
    showDeleteDialog: (BookInfoEvent.OnShowDeleteDialog) -> Unit,
    navigateToReader: () -> Unit
) {
    LazyColumnWithScrollbar(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        scrollbarSettings = providePrimaryScrollbar(false),
        contentPadding = PaddingValues(bottom = 18.dp)
    ) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                if (book.coverImage != null) {
                    BookInfoLayoutBackground(
                        height = paddingValues.calculateTopPadding() + 232.dp,
                        image = book.coverImage
                    )
                }

                Column(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(paddingValues.calculateTopPadding() + 12.dp))
                    BookInfoLayoutInfo(
                        book = book,
                        showTitleDialog = showTitleDialog,
                        showAuthorDialog = showAuthorDialog,
                        showChangeCoverBottomSheet = showChangeCoverBottomSheet
                    )
                }
            }
        }

        item {
            Spacer(Modifier.height(18.dp))
            BookInfoLayoutActions(
                showMoveDialog = showMoveDialog,
                showDeleteDialog = showDeleteDialog
            )
        }

        item {
            Spacer(Modifier.height(18.dp))
            BookInfoLayoutDescription(
                book = book,
                showDescriptionDialog = showDescriptionDialog
            )
        }

        item {
            Spacer(Modifier.height(18.dp))
            BookInfoLayoutButton(
                book = book,
                navigateToReader = navigateToReader
            )
        }
    }
}
