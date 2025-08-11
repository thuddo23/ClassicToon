/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.library

import android.os.Parcelable
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import com.classictoon.novel.R
import com.classictoon.novel.domain.library.category.Category
import com.classictoon.novel.domain.library.category.CategoryWithBooks
import com.classictoon.novel.domain.navigator.Screen
import com.classictoon.novel.domain.ui.UIText
import com.classictoon.novel.presentation.library.LibraryContent
import com.classictoon.novel.presentation.navigator.LocalNavigator
import com.classictoon.novel.ui.book_info.BookInfoScreen
import com.classictoon.novel.ui.browse.BrowseScreen
import com.classictoon.novel.ui.history.HistoryScreen
import com.classictoon.novel.ui.main.MainModel
import com.classictoon.novel.ui.reader.ReaderScreen

@Parcelize
object LibraryScreen : Screen, Parcelable {

    @IgnoredOnParcel
    const val MOVE_DIALOG = "move_dialog"

    @IgnoredOnParcel
    const val DELETE_DIALOG = "delete_dialog"

    @IgnoredOnParcel
    val refreshListChannel: Channel<Long> = Channel(Channel.CONFLATED)

    @IgnoredOnParcel
    val scrollToPageCompositionChannel: Channel<Int> = Channel(Channel.CONFLATED)

    @IgnoredOnParcel
    private var initialPage = 0

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val screenModel = hiltViewModel<LibraryModel>()
        val mainModel = hiltViewModel<MainModel>()

        val state = screenModel.state.collectAsStateWithLifecycle()
        val mainState = mainModel.state.collectAsStateWithLifecycle()

        val categories = remember(state.value.books) {
            derivedStateOf {
                listOf(
                    CategoryWithBooks(
                        category = Category.FANTASY,
                        title = UIText.StringResource(R.string.reading_tab),
                        books = state.value.books.filter { Category.FANTASY in it.data.category }
                    ),
                    CategoryWithBooks(
                        category = Category.ROMANCE,
                        title = UIText.StringResource(R.string.already_read_tab),
                        books = state.value.books.filter { Category.ROMANCE in it.data.category }
                    ),
                    CategoryWithBooks(
                        category = Category.ACTION,
                        title = UIText.StringResource(R.string.planning_tab),
                        books = state.value.books.filter { Category.ACTION in it.data.category }
                    ),
                    CategoryWithBooks(
                        category = Category.THRILLER,
                        title = UIText.StringResource(R.string.dropped_tab),
                        books = state.value.books.filter { Category.THRILLER in it.data.category }
                    ),
                    CategoryWithBooks(
                        category = Category.COMEDY,
                        title = UIText.StringResource(R.string.comedy_tab),
                        books = state.value.books.filter { Category.COMEDY in it.data.category }
                    ),
                    CategoryWithBooks(
                        category = Category.DRAMA,
                        title = UIText.StringResource(R.string.drama_tab),
                        books = state.value.books.filter { Category.DRAMA in it.data.category }
                    ),
                    CategoryWithBooks(
                        category = Category.MYSTERY,
                        title = UIText.StringResource(R.string.mystery_tab),
                        books = state.value.books.filter { Category.MYSTERY in it.data.category }
                    ),
                    CategoryWithBooks(
                        category = Category.SCIENCE_FICTION,
                        title = UIText.StringResource(R.string.science_fiction_tab),
                        books = state.value.books.filter { Category.SCIENCE_FICTION in it.data.category }
                    ),
                    CategoryWithBooks(
                        category = Category.ALL,
                        title = UIText.StringResource(R.string.other_tab),
                        books = state.value.books
                    )
                )
            }
        }

        val focusRequester = remember { FocusRequester() }
        val refreshState = rememberPullRefreshState(
            refreshing = state.value.isRefreshing,
            onRefresh = {
                screenModel.onEvent(
                    LibraryEvent.OnRefreshList(
                        loading = false,
                        hideSearch = true
                    )
                )
            }
        )

        val pagerState = rememberPagerState(
            initialPage = initialPage
        ) { categories.value.count() }
        DisposableEffect(Unit) { onDispose { initialPage = pagerState.currentPage } }

        LaunchedEffect(Unit) {
            scrollToPageCompositionChannel.receiveAsFlow().collectLatest {
                pagerState.animateScrollToPage(it)
            }
        }

        LibraryContent(
            books = state.value.books,
            selectedItemsCount = state.value.selectedItemsCount,
            hasSelectedItems = state.value.hasSelectedItems,
            showSearch = state.value.showSearch,
            searchQuery = state.value.searchQuery,
            bookCount = state.value.books.count(),
            focusRequester = focusRequester,
            pagerState = pagerState,
            isLoading = state.value.isLoading,
            isRefreshing = state.value.isRefreshing,
            doublePressExit = mainState.value.doublePressExit,
            categories = categories.value,
            refreshState = refreshState,
            dialog = state.value.dialog,
            selectBook = screenModel::onEvent,
            searchVisibility = screenModel::onEvent,
            requestFocus = screenModel::onEvent,
            searchQueryChange = screenModel::onEvent,
            search = screenModel::onEvent,
            clearSelectedBooks = screenModel::onEvent,
            showMoveDialog = screenModel::onEvent,
            actionMoveDialog = screenModel::onEvent,
            actionDeleteDialog = screenModel::onEvent,
            showDeleteDialog = screenModel::onEvent,
            dismissDialog = screenModel::onEvent,
            navigateToBrowse = {
                navigator.push(BrowseScreen)
            },
            navigateToReader = {
                HistoryScreen.insertHistoryChannel.trySend(it)
                navigator.push(ReaderScreen(it))
            },
            navigateToBookInfo = {
                navigator.push(BookInfoScreen(bookId = it))
            }
        )
    }
}
