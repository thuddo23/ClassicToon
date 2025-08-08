/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.parser

import com.classictoon.novel.domain.file.CachedFile
import com.classictoon.novel.domain.library.book.BookWithCover


interface FileParser {

    suspend fun parse(cachedFile: CachedFile): BookWithCover?
}
