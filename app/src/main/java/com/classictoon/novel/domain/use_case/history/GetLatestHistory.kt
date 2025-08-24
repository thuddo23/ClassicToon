/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.use_case.history

import com.classictoon.novel.domain.history.History
import com.classictoon.novel.domain.repository.HistoryRepository
import javax.inject.Inject

class GetLatestHistory @Inject constructor(
    private val repository: HistoryRepository
) {

    suspend fun execute(bookId: Int): History? {
        return repository.getLatestBookHistory(bookId)
    }
}
