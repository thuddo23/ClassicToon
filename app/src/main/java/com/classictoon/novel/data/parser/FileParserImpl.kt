/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.parser

import android.util.Log
import com.classictoon.novel.data.parser.epub.EpubFileParser
import com.classictoon.novel.data.parser.fb2.Fb2FileParser
import com.classictoon.novel.data.parser.html.HtmlFileParser
import com.classictoon.novel.data.parser.pdf.PdfFileParser
import com.classictoon.novel.data.parser.txt.TxtFileParser
import com.classictoon.novel.domain.file.CachedFile
import com.classictoon.novel.domain.library.book.BookWithCover
import javax.inject.Inject

private const val FILE_PARSER = "File Parser"

class FileParserImpl @Inject constructor(
    private val txtFileParser: TxtFileParser,
    private val pdfFileParser: PdfFileParser,
    private val epubFileParser: EpubFileParser,
    private val fb2FileParser: Fb2FileParser,
    private val htmlFileParser: HtmlFileParser,
) : FileParser {

    override suspend fun parse(cachedFile: CachedFile): BookWithCover? {
        if (!cachedFile.canAccess()) {
            Log.e(FILE_PARSER, "File does not exist or no read access is granted.")
            return null
        }

        val fileFormat = ".${cachedFile.name.substringAfterLast(".")}".lowercase().trim()
        return when (fileFormat) {
            ".pdf" -> {
                pdfFileParser.parse(cachedFile)
            }

            ".epub" -> {
                epubFileParser.parse(cachedFile)
            }

            ".txt" -> {
                txtFileParser.parse(cachedFile)
            }

            ".fb2" -> {
                fb2FileParser.parse(cachedFile)
            }

            ".html" -> {
                htmlFileParser.parse(cachedFile)
            }

            ".htm" -> {
                htmlFileParser.parse(cachedFile)
            }

            ".md" -> {
                txtFileParser.parse(cachedFile)
            }

            else -> {
                Log.e(FILE_PARSER, "Wrong file format, could not find supported extension.")
                null
            }
        }
    }
}
