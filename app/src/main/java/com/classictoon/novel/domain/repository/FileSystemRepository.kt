/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.repository

import com.classictoon.novel.domain.browse.file.SelectableFile
import com.classictoon.novel.domain.file.CachedFile
import com.classictoon.novel.domain.library.book.NullableBook

interface FileSystemRepository {

    suspend fun getFiles(
        query: String = ""
    ): List<SelectableFile>

    suspend fun getBookFromFile(
        cachedFile: CachedFile
    ): NullableBook
}
