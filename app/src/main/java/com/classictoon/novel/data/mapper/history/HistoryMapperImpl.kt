/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.mapper.history

import com.classictoon.novel.data.local.dto.HistoryEntity
import com.classictoon.novel.domain.history.History
import javax.inject.Inject

class HistoryMapperImpl @Inject constructor() : HistoryMapper {
    override suspend fun toHistoryEntity(history: History): HistoryEntity {
        return HistoryEntity(
            id = history.id,
            bookId = history.bookId,
            time = history.time
        )
    }

    override suspend fun toHistory(historyEntity: HistoryEntity): History {
        return History(
            historyEntity.id,
            bookId = historyEntity.bookId,
            book = null,
            time = historyEntity.time
        )
    }
}
