/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.use_case.color_preset

import com.classictoon.novel.domain.reader.ColorPreset
import com.classictoon.novel.domain.repository.ColorPresetRepository
import javax.inject.Inject

class ReorderColorPresets @Inject constructor(
    private val repository: ColorPresetRepository
) {

    suspend fun execute(reorderedColorPresets: List<ColorPreset>) {
        repository.reorderColorPresets(reorderedColorPresets)
    }
}
