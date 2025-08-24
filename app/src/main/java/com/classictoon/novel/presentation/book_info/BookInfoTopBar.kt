/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.book_info

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import com.classictoon.novel.R
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.presentation.core.components.common.IconButton
import com.classictoon.novel.presentation.core.components.common.StyledText
import com.classictoon.novel.presentation.core.components.top_bar.TopAppBar
import com.classictoon.novel.presentation.core.components.top_bar.TopAppBarData
import com.classictoon.novel.presentation.navigator.NavigatorBackIconButton
import com.classictoon.novel.ui.book_info.BookInfoEvent
import com.classictoon.novel.ui.theme.DefaultTransition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookInfoTopBar(
    book: Book,
    listState: LazyListState,
    showDetailsBottomSheet: (BookInfoEvent.OnShowDetailsBottomSheet) -> Unit,
    navigateBack: () -> Unit
) {
    val firstVisibleItemIndex = remember {
        derivedStateOf {
            listState.firstVisibleItemIndex
        }
    }

    TopAppBar(
        containerColor = MaterialTheme.colorScheme.surface.copy(0f),
        scrollBehavior = null,
        isTopBarScrolled = listState.canScrollBackward,

        shownTopBar = 0,
        topBars = listOf(
            TopAppBarData(
                contentID = 0,
                contentNavigationIcon = {
                    NavigatorBackIconButton {
                        navigateBack()
                    }
                },
                contentTitle = {
                    DefaultTransition(firstVisibleItemIndex.value > 0) {
                        StyledText(
                            text = book.title,
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            maxLines = 1
                        )
                    }
                },
                contentActions = {
                    IconButton(
                        icon = Icons.Outlined.Info,
                        contentDescription = R.string.file_details,
                        disableOnClick = false,
                        onClick = {
                            showDetailsBottomSheet(BookInfoEvent.OnShowDetailsBottomSheet)
                        }
                    )
                }
            )
        )
    )
}
