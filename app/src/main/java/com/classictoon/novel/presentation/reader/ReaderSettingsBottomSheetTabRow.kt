/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.reader

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.classictoon.novel.R
import com.classictoon.novel.presentation.core.components.modal_bottom_sheet.ModalBottomSheetTabRow

@Composable
fun ReaderSettingsBottomSheetTabRow(
    currentPage: Int,
    scrollToPage: (Int) -> Unit
) {
    val tabItems = listOf(
        stringResource(id = R.string.general_tab),
        stringResource(id = R.string.reader_tab),
        stringResource(id = R.string.color_tab)
    )

    ModalBottomSheetTabRow(
        selectedTabIndex = currentPage,
        tabs = tabItems
    ) { index ->
        scrollToPage(index)
    }
}
