/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.mapper.color_preset

import com.classictoon.novel.data.local.dto.ColorPresetEntity
import com.classictoon.novel.domain.reader.ColorPreset

interface ColorPresetMapper {
    suspend fun toColorPresetEntity(colorPreset: ColorPreset, order: Int): ColorPresetEntity

    suspend fun toColorPreset(colorPresetEntity: ColorPresetEntity): ColorPreset
}
