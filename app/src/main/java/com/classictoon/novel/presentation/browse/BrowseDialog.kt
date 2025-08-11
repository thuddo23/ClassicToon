/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.browse

import androidx.compose.runtime.Composable
import com.classictoon.novel.domain.library.book.SelectableNullableBook
import com.classictoon.novel.domain.util.Dialog
import com.classictoon.novel.ui.browse.BrowseEvent
import com.classictoon.novel.ui.browse.BrowseScreen

@Composable
fun BrowseDialog(
    dialog: Dialog?,
    loadingAddDialog: Boolean,
    selectedBooksAddDialog: List<SelectableNullableBook>,
    dismissAddDialog: (BrowseEvent.OnDismissAddDialog) -> Unit,
    actionAddDialog: (BrowseEvent.OnActionAddDialog) -> Unit,
    selectAddDialog: (BrowseEvent.OnSelectAddDialog) -> Unit,
    navigateToHome: () -> Unit
) {
    when (dialog) {
        BrowseScreen.ADD_DIALOG -> {
            BrowseAddDialog(
                loadingAddDialog = loadingAddDialog,
                selectedBooksAddDialog = selectedBooksAddDialog,
                dismissAddDialog = dismissAddDialog,
                actionAddDialog = actionAddDialog,
                selectAddDialog = selectAddDialog,
                navigateToHome = navigateToHome
            )
        }
    }
}
