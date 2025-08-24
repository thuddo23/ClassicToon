/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.parser.html

import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import com.classictoon.novel.R
import com.classictoon.novel.data.parser.FileParser
import com.classictoon.novel.domain.file.CachedFile
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.library.book.BookWithCover
import com.classictoon.novel.domain.library.category.Category
import com.classictoon.novel.domain.library.category.DEFAULT_CATEGORY
import com.classictoon.novel.domain.ui.UIText
import javax.inject.Inject

class HtmlFileParser @Inject constructor() : FileParser {

    override suspend fun parse(cachedFile: CachedFile): BookWithCover? {
        return try {
            val document = cachedFile.openInputStream()?.use {
                Jsoup.parse(it, null, "", Parser.htmlParser())
            }

            val title = document?.select("head > title")?.text()?.trim().run {
                if (isNullOrBlank()) {
                    return@run cachedFile.name.substringBeforeLast(".").trim()
                }
                return@run this
            }

            BookWithCover(
                book = Book(
                    title = title,
                    author = UIText.StringResource(R.string.unknown_author),
                    description = null,
                    scrollIndex = 0,
                    scrollOffset = 0,
                    progress = 0f,
                    filePath = cachedFile.path,
                    lastOpened = null,
                    category = DEFAULT_CATEGORY,
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
