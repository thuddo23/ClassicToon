/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.parser.txt

import com.classictoon.novel.R
import com.classictoon.novel.data.parser.FileParser
import com.classictoon.novel.domain.file.CachedFile
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.library.book.BookWithCover
import com.classictoon.novel.domain.library.category.Category
import com.classictoon.novel.domain.ui.UIText
import javax.inject.Inject

class TxtFileParser @Inject constructor() : FileParser {

    override suspend fun parse(cachedFile: CachedFile): BookWithCover? {
        return try {
            val title = cachedFile.name.substringBeforeLast(".").trim()
            val author = UIText.StringResource(R.string.unknown_author)

            BookWithCover(
                book = Book(
                    title = title,
                    author = author,
                    description = null,
                    scrollIndex = 0,
                    scrollOffset = 0,
                    progress = 0f,
                    filePath = cachedFile.path,
                    lastOpened = null,
                    category = Category.entries[0],
                    coverImage = null
                ),
                coverImage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
