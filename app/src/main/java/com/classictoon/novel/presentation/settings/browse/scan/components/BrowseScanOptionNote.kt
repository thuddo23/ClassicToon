/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.settings.browse.scan.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.classictoon.novel.R
import com.classictoon.novel.presentation.settings.components.SettingsSubcategoryNote

@Composable
fun BrowseScanOptionNote() {
    SettingsSubcategoryNote(
        text = stringResource(
            id = R.string.browse_scan_option_note
        )
    )
}
