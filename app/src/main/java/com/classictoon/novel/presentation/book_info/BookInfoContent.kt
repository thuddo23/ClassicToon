/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.book_info

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.util.BottomSheet
import com.classictoon.novel.domain.util.Dialog
import com.classictoon.novel.ui.book_info.BookInfoEvent

@Composable
fun BookInfoContent(
    book: Book,
    bottomSheet: BottomSheet?,
    dialog: Dialog?,
    listState: LazyListState,
    canResetCover: Boolean,
    showChangeCoverBottomSheet: (BookInfoEvent.OnShowChangeCoverBottomSheet) -> Unit,
    showDetailsBottomSheet: (BookInfoEvent.OnShowDetailsBottomSheet) -> Unit,
    showTitleDialog: (BookInfoEvent.OnShowTitleDialog) -> Unit,
    actionTitleDialog: (BookInfoEvent.OnActionTitleDialog) -> Unit,
    showAuthorDialog: (BookInfoEvent.OnShowAuthorDialog) -> Unit,
    actionAuthorDialog: (BookInfoEvent.OnActionAuthorDialog) -> Unit,
    showDescriptionDialog: (BookInfoEvent.OnShowDescriptionDialog) -> Unit,
    actionDescriptionDialog: (BookInfoEvent.OnActionDescriptionDialog) -> Unit,
    showPathDialog: (BookInfoEvent.OnShowPathDialog) -> Unit,
    actionPathDialog: (BookInfoEvent.OnActionPathDialog) -> Unit,
    showMoveDialog: (BookInfoEvent.OnShowMoveDialog) -> Unit,
    showDeleteDialog: (BookInfoEvent.OnShowDeleteDialog) -> Unit,
    actionDeleteDialog: (BookInfoEvent.OnActionDeleteDialog) -> Unit,
    actionMoveDialog: (BookInfoEvent.OnActionMoveDialog) -> Unit,
    changeCover: (BookInfoEvent.OnChangeCover) -> Unit,
    resetCover: (BookInfoEvent.OnResetCover) -> Unit,
    deleteCover: (BookInfoEvent.OnDeleteCover) -> Unit,
    checkCoverReset: (BookInfoEvent.OnCheckCoverReset) -> Unit,
    dismissDialog: (BookInfoEvent.OnDismissDialog) -> Unit,
    dismissBottomSheet: (BookInfoEvent.OnDismissBottomSheet) -> Unit,
    navigateToReader: () -> Unit,
    navigateToHome: () -> Unit,
    navigateBack: () -> Unit
) {
    BookInfoDialog(
        dialog = dialog,
        book = book,
        actionTitleDialog = actionTitleDialog,
        actionAuthorDialog = actionAuthorDialog,
        actionDescriptionDialog = actionDescriptionDialog,
        actionPathDialog = actionPathDialog,
        actionDeleteDialog = actionDeleteDialog,
        actionMoveDialog = actionMoveDialog,
        dismissDialog = dismissDialog,
        navigateToHome = navigateToHome,
        navigateBack = navigateBack
    )

    BookInfoBottomSheet(
        bottomSheet = bottomSheet,
        book = book,
        showPathDialog = showPathDialog,
        canResetCover = canResetCover,
        changeCover = changeCover,
        resetCover = resetCover,
        deleteCover = deleteCover,
        checkCoverReset = checkCoverReset,
        dismissBottomSheet = dismissBottomSheet
    )

    BookInfoScaffold(
        book = book,
        listState = listState,
        showTitleDialog = showTitleDialog,
        showAuthorDialog = showAuthorDialog,
        showDescriptionDialog = showDescriptionDialog,
        showChangeCoverBottomSheet = showChangeCoverBottomSheet,
        showDetailsBottomSheet = showDetailsBottomSheet,
        showMoveDialog = showMoveDialog,
        showDeleteDialog = showDeleteDialog,
        navigateToReader = navigateToReader,
        navigateBack = navigateBack
    )

    BookInfoBackHandler(
        navigateBack = navigateBack
    )
}
