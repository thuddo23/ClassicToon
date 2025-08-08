/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("FunctionName")

package com.classictoon.novel.presentation.start

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.classictoon.novel.R
import com.classictoon.novel.domain.ui.ButtonItem
import com.classictoon.novel.presentation.core.components.dialog.SelectableDialogItem
import com.classictoon.novel.presentation.settings.components.SettingsSubcategoryTitle
import com.classictoon.novel.ui.main.MainEvent

fun LazyListScope.StartSettingsLayoutGeneral(
    languages: List<ButtonItem>,
    changeLanguage: (MainEvent.OnChangeLanguage) -> Unit
) {
    item {
        Spacer(modifier = Modifier.height(16.dp))
        SettingsSubcategoryTitle(
            title = stringResource(id = R.string.start_language_preferences),
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(12.dp))
    }

    items(languages, key = { it.id }) {
        SelectableDialogItem(
            selected = it.selected,
            title = it.title,
            horizontalPadding = 18.dp
        ) {
            changeLanguage(MainEvent.OnChangeLanguage(it.id))
        }
    }

    item {
        Spacer(modifier = Modifier.height(8.dp))
    }
}
