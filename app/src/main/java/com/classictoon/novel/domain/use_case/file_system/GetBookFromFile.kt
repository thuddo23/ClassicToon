/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.use_case.file_system

import com.classictoon.novel.domain.file.CachedFile
import com.classictoon.novel.domain.library.book.NullableBook
import com.classictoon.novel.domain.repository.FileSystemRepository
import javax.inject.Inject

class GetBookFromFile @Inject constructor(
    private val repository: FileSystemRepository
) {

    suspend fun execute(cachedFile: CachedFile): NullableBook {
        return repository.getBookFromFile(cachedFile)
    }
}
