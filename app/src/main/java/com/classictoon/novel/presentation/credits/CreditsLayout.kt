/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.credits

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.classictoon.novel.presentation.core.components.common.LazyColumnWithScrollbar
import com.classictoon.novel.presentation.core.constants.provideCredits
import com.classictoon.novel.ui.about.AboutEvent

@Composable
fun CreditsLayout(
    paddingValues: PaddingValues,
    listState: LazyListState,
    navigateToBrowserPage: (AboutEvent.OnNavigateToBrowserPage) -> Unit
) {
    val context = LocalContext.current
    LazyColumnWithScrollbar(
        Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
        state = listState
    ) {
        items(
            provideCredits(),
            key = { it.name }
        ) { credit ->
            CreditItem(credit = credit) {
                credit.website?.let { website ->
                    navigateToBrowserPage(
                        AboutEvent.OnNavigateToBrowserPage(
                            page = website,
                            context = context
                        )
                    )
                }
            }
        }
    }
}
