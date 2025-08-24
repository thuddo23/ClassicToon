/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("FunctionName")

package com.classictoon.novel.presentation.settings.browse

import androidx.compose.foundation.lazy.LazyListScope
import com.classictoon.novel.presentation.settings.browse.display.BrowseDisplaySubcategory
import com.classictoon.novel.presentation.settings.browse.filter.BrowseFilterSubcategory
import com.classictoon.novel.presentation.settings.browse.scan.BrowseScanSubcategory
import com.classictoon.novel.presentation.settings.browse.sort.BrowseSortSubcategory

fun LazyListScope.BrowseSettingsCategory() {
    BrowseScanSubcategory()
    BrowseDisplaySubcategory()
    BrowseFilterSubcategory()
    BrowseSortSubcategory(
        showDivider = false
    )
}
