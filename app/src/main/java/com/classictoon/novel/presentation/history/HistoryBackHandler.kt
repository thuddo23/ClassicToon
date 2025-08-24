/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.history

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.classictoon.novel.ui.history.HistoryEvent

@Composable
fun HistoryBackHandler(
    showSearch: Boolean,
    searchVisibility: (HistoryEvent.OnSearchVisibility) -> Unit,
    navigateToHome: () -> Unit
) {
    BackHandler {
        if (showSearch) {
            searchVisibility(HistoryEvent.OnSearchVisibility(false))
            return@BackHandler
        }

        navigateToHome()
    }
}
