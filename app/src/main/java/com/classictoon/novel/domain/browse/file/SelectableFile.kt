/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.browse.file

import androidx.compose.runtime.Immutable
import com.classictoon.novel.domain.file.CachedFile
import com.classictoon.novel.domain.util.Selected

@Immutable
data class SelectableFile(
    val data: CachedFile,
    val selected: Selected
)
