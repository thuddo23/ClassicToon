/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.library

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.classictoon.novel.R
import com.classictoon.novel.presentation.core.components.dialog.Dialog
import com.classictoon.novel.ui.library.LibraryEvent

@Composable
fun LibraryDeleteDialog(
    selectedItemsCount: Int,
    actionDeleteDialog: (LibraryEvent.OnActionDeleteDialog) -> Unit,
    dismissDialog: (LibraryEvent.OnDismissDialog) -> Unit
) {
    val context = LocalContext.current

    Dialog(
        title = stringResource(id = R.string.delete_books),
        icon = Icons.Outlined.DeleteOutline,
        description = stringResource(
            id = R.string.delete_books_description,
            selectedItemsCount
        ),
        actionEnabled = true,
        onDismiss = {
            dismissDialog(LibraryEvent.OnDismissDialog)
        },
        onAction = {
            actionDeleteDialog(
                LibraryEvent.OnActionDeleteDialog(
                    context = context
                )
            )
        },
        withContent = false
    )
}
