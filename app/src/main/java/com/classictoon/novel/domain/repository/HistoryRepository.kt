/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.repository

import com.classictoon.novel.domain.history.History

interface HistoryRepository {

    suspend fun insertHistory(
        history: History
    )

    suspend fun getHistory(): List<History>

    suspend fun getLatestBookHistory(
        bookId: Int
    ): History?

    suspend fun deleteWholeHistory()

    suspend fun deleteBookHistory(
        bookId: Int
    )

    suspend fun deleteHistory(
        history: History
    )
}
