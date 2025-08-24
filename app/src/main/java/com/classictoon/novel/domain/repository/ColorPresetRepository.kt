/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.repository

import com.classictoon.novel.domain.reader.ColorPreset

interface ColorPresetRepository {

    suspend fun updateColorPreset(
        colorPreset: ColorPreset
    )

    suspend fun selectColorPreset(
        colorPreset: ColorPreset
    )

    suspend fun getColorPresets(): List<ColorPreset>

    suspend fun reorderColorPresets(
        orderedColorPresets: List<ColorPreset>
    )

    suspend fun deleteColorPreset(
        colorPreset: ColorPreset
    )
}
