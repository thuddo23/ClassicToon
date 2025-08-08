/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.parser

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.classictoon.novel.data.parser.epub.EpubTextParser
import com.classictoon.novel.data.parser.html.HtmlTextParser
import com.classictoon.novel.data.parser.pdf.PdfTextParser
import com.classictoon.novel.data.parser.txt.TxtTextParser
import com.classictoon.novel.data.parser.xml.XmlTextParser
import com.classictoon.novel.domain.file.CachedFile
import com.classictoon.novel.domain.reader.ReaderText
import javax.inject.Inject

private const val TEXT_PARSER = "Text Parser"

class TextParserImpl @Inject constructor(
    // Markdown parser (Markdown)
    private val txtTextParser: TxtTextParser,
    private val pdfTextParser: PdfTextParser,

    // Document parser (HTML+Markdown)
    private val epubTextParser: EpubTextParser,
    private val htmlTextParser: HtmlTextParser,
    private val xmlTextParser: XmlTextParser
) : TextParser {

    override suspend fun parse(cachedFile: CachedFile): List<ReaderText> {
        if (!cachedFile.canAccess()) {
            Log.e(TEXT_PARSER, "File does not exist or no read access is granted.")
            return emptyList()
        }

        val fileFormat = ".${cachedFile.name.substringAfterLast(".")}".lowercase().trim()
        return withContext(Dispatchers.IO) {
            when (fileFormat) {
                ".pdf" -> {
                    pdfTextParser.parse(cachedFile)
                }

                ".epub" -> {
                    epubTextParser.parse(cachedFile)
                }

                ".txt" -> {
                    txtTextParser.parse(cachedFile)
                }

                ".fb2" -> {
                    xmlTextParser.parse(cachedFile)
                }

                ".html" -> {
                    htmlTextParser.parse(cachedFile)
                }

                ".htm" -> {
                    htmlTextParser.parse(cachedFile)
                }

                ".md" -> {
                    htmlTextParser.parse(cachedFile)
                }

                else -> {
                    Log.e(TEXT_PARSER, "Wrong file format, could not find supported extension.")
                    emptyList()
                }
            }
        }
    }
}
