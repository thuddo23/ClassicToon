/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.mapper.history

import com.classictoon.novel.data.local.dto.HistoryEntity
import com.classictoon.novel.domain.history.History

interface HistoryMapper {
    suspend fun toHistoryEntity(history: History): HistoryEntity

    suspend fun toHistory(historyEntity: HistoryEntity): History
}
