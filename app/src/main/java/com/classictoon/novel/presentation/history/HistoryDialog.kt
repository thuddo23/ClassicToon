/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.history

import androidx.compose.runtime.Composable
import com.classictoon.novel.domain.util.Dialog
import com.classictoon.novel.ui.history.HistoryEvent
import com.classictoon.novel.ui.history.HistoryScreen

@Composable
fun HistoryDialog(
    dialog: Dialog?,
    actionDeleteWholeHistoryDialog: (HistoryEvent.OnActionDeleteWholeHistoryDialog) -> Unit,
    dismissDialog: (HistoryEvent.OnDismissDialog) -> Unit
) {
    when (dialog) {
        HistoryScreen.DELETE_WHOLE_HISTORY_DIALOG -> {
            HistoryDeleteWholeHistoryDialog(
                actionDeleteWholeHistoryDialog = actionDeleteWholeHistoryDialog,
                dismissDialog = dismissDialog
            )
        }
    }
}
