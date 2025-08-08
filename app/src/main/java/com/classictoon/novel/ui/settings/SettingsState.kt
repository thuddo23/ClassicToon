/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.settings

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Immutable
import com.classictoon.novel.domain.reader.ColorPreset
import com.classictoon.novel.presentation.core.constants.provideDefaultColorPreset

@Immutable
data class SettingsState(
    val colorPresets: List<ColorPreset> = emptyList(),
    val selectedColorPreset: ColorPreset = provideDefaultColorPreset(),
    val animateColorPreset: Boolean = false,
    val colorPresetListState: LazyListState = LazyListState()
)
