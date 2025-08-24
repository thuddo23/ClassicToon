/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.parser

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.yield
import org.jsoup.nodes.Document
import com.classictoon.novel.domain.reader.ReaderText
import com.classictoon.novel.presentation.core.util.addAll
import com.classictoon.novel.presentation.core.util.clearAllMarkdown
import com.classictoon.novel.presentation.core.util.clearMarkdown
import com.classictoon.novel.presentation.core.util.containsVisibleText
import org.jsoup.nodes.Element
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import javax.inject.Inject

class DocumentParser @Inject constructor(
    private val markdownParser: MarkdownParser
) {
    /**
     * Parses document to get it's text.
     * Fixes issues such as manual line breaking in <p>.
     * Applies Markdown to the text: Bold(**), Italic(_), Section separator(---), and Links(a > href).
     * Now also handles chapter-tab divs for HTML books with proper chapter structure.
     *
     * @return Parsed text line by line with Markdown(all lines are not blank).
     */
    suspend fun parseDocument(
        document: Document,
        zipFile: ZipFile? = null,
        imageEntries: List<ZipEntry>? = null,
        includeChapter: Boolean = true
    ): List<ReaderText> {
        yield()

        val readerText = mutableListOf<ReaderText>()
        var chapterAdded = false

        // Check if this is an HTML book with chapter-tab structure
        val hasChapterTabs = document.select("div.chapter-tab").isNotEmpty()

        if (hasChapterTabs) {
            val result = parseHtmlBookWithChapters(document, zipFile, imageEntries)
            if (result.isNotEmpty()) return result
        } else {
            val body = document.selectFirst("body")
                .run { this ?: document.body() }
            val result = handleContent(body, zipFile, imageEntries, includeChapter)
            readerText.addAll(result)
            yield()
            android.util.Log.d(
                "DocumentParser",
                "Using standard HTML parsing (no chapter-tab structure detected or chapter parsing failed)"
            )
        }

        if (
            readerText.filterIsInstance<ReaderText.Text>().isEmpty() ||
            (includeChapter && readerText.filterIsInstance<ReaderText.Chapter>().isEmpty())
        ) {
            return emptyList()
        }

        return readerText
    }

    /**
     * Parse HTML book with chapter-tab structure
     */
    private suspend fun parseHtmlBookWithChapters(
        document: Document,
        zipFile: ZipFile? = null,
        imageEntries: List<ZipEntry>? = null
    ): List<ReaderText> {
        val readerText = mutableListOf<ReaderText>()
        
        try {
            // Process each chapter-tab div
            val chapterTabs = document.select("div.chapter-tab")

            if (chapterTabs.isEmpty()) {
                return emptyList()
            }
            
            chapterTabs.forEachIndexed { index, chapterDiv ->
                yield()
                
                try {
                    // Extract chapter title from h2
                    val h2Element = chapterDiv.selectFirst("h2")
                    val chapterTitle = h2Element?.text()?.trim() ?: "Unknown Chapter"

                    // Add chapter
                    readerText.add(
                        ReaderText.Chapter(
                            title = chapterTitle,
                            nested = false
                        )
                    )
                    
                    // Process content within the chapter
                    val chapterContent = handleContent(element = chapterDiv, zipFile = zipFile, imageEntries = imageEntries, includeChapter = false)
                    readerText.addAll(chapterContent)
                    

                    // Add separator between chapters (except after the last one)
                    if (chapterDiv != chapterTabs.last()) {
                        readerText.add(ReaderText.Separator)
                    }
                } catch (e: Exception) {
                    android.util.Log.e("DocumentParser", "Error processing chapter ${index + 1}: ${e.message}")
                    e.printStackTrace()
                }
            }
            
            android.util.Log.d("DocumentParser", "HTML book parsing completed. Total elements: ${readerText.size}")
            
            // Validate that we have at least some content
            if (readerText.filterIsInstance<ReaderText.Text>().isEmpty()) {
                android.util.Log.w("DocumentParser", "No text content found in HTML book")
                return emptyList()
            }
            
            return readerText
            
        } catch (e: Exception) {
            android.util.Log.e("DocumentParser", "Error parsing HTML book with chapters: ${e.message}")
            e.printStackTrace()
            return emptyList()
        }
    }


    private suspend fun handleContent(
        element: Element,
        zipFile: ZipFile? = null,
        imageEntries: List<ZipEntry>? = null,
        includeChapter: Boolean = true
    ): List<ReaderText> {
        val readerText = mutableListOf<ReaderText>()
        var chapterAdded = false

        element
            .apply {
                // Remove manual line breaks from all <p>, <a>
                select("p").forEach { element ->
                    yield()
                    element.html(element.html().replace(Regex("\\n+"), " "))
                    element.append("\n")
                }
                select("a").forEach { element ->
                    yield()
                    element.html(element.html().replace(Regex("\\n+"), ""))
                }

                // Remove <head>'s title
                select("title").remove()

                // Markdown
                select("hr").append("\n---\n")
                select("b").append("**").prepend("**")
                select("h1").append("**").prepend("**")
                select("h2").append("**").prepend("**")
                select("h3").append("**").prepend("**")
                select("strong").append("**").prepend("**")
                select("em").append("_").prepend("_")
                select("a").forEach { element ->
                    var link = element.attr("href")
                    if (!link.startsWith("http") || element.wholeText().isBlank()) return@forEach

                    if (link.startsWith("http://")) {
                        link = link.replace("http://", "https://")
                    }

                    element.prepend("[")
                    element.append("]($link)")
                }

                // Image (<img>)
                select("img").forEach { element ->
                    val src = element.attr("src").trim()
                    val alt = element.attr("alt").trim().takeIf {
                        it.clearMarkdown().containsVisibleText()
                    } ?: src.substringBeforeLast(".")

                    // Check if this is a remote image (HTTP/HTTPS URL)
                    if (src.startsWith("http://") || src.startsWith("https://")) {
                        // Handle remote image
                        element.append("\n[[REMOTE:$src|$alt]]\n")
                    } else {
                        // Handle local image (existing logic)
                        val localSrc = src.substringAfterLast(File.separator)
                            .lowercase()
                            .takeIf {
                                it.containsVisibleText() && imageEntries?.any { image ->
                                    it == image.name.substringAfterLast(File.separator).lowercase()
                                } == true
                            } ?: return@forEach

                        element.append("\n[[$localSrc|$alt]]\n")
                    }
                }

                // Image (<image>)
                select("image").forEach { element ->
                    val src = element.attr("xlink:href")
                        .trim()
                        .substringAfterLast(File.separator)
                        .lowercase()
                        .takeIf {
                            it.containsVisibleText() && imageEntries?.any { image ->
                                it == image.name.substringAfterLast(File.separator).lowercase()
                            } == true
                        } ?: return@forEach

                    val alt = src.substringBeforeLast(".")

                    element.append("\n[[$src|$alt]]\n")
                }
            }.wholeText().lines().forEach { line ->
                yield()

                val formattedLine = line.replace(
                    Regex("""\*\*\*\s*(.*?)\s*\*\*\*"""), "_**$1**_"
                ).replace(
                    Regex("""\*\*\s*(.*?)\s*\*\*"""), "**$1**"
                ).replace(
                    Regex("""_\s*(.*?)\s*_"""), "_$1_"
                ).trim()

                val imageRegex = Regex("""\[\[(.*?)\|(.*?)]]""")

                if (line.containsVisibleText()) {
                    when {
                        imageRegex.matches(line) -> {
                            val trimmedLine = line.removeSurrounding("[[", "]]")
                            val src = trimmedLine.substringBefore("|")
                            val alt = "_${trimmedLine.substringAfter("|")}_"

                            // Check if this is a remote image
                            if (src.startsWith("REMOTE:")) {
                                val remoteUrl = src.substringAfter("REMOTE:")
                                readerText.add( // Adding remote image
                                    ReaderText.RemoteImage(
                                        url = remoteUrl
                                    )
                                )
                                readerText.add( // Adding alternative text (caption) for image
                                    ReaderText.Text(
                                        markdownParser.parse(alt)
                                    )
                                )
                            } else {
                                // Handle local image (existing logic)
                                val image = try {
                                    val imageEntry = imageEntries?.find { image ->
                                        src == image.name.substringAfterLast(File.separator).lowercase()
                                    } ?: return@forEach

                                    zipFile?.getImage(imageEntry)?.asImageBitmap()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    null
                                } ?: return@forEach

                                image.prepareToDraw()
                                readerText.add( // Adding image
                                    ReaderText.Image(
                                        imageBitmap = image
                                    )
                                )
                                readerText.add( // Adding alternative text (caption) for image
                                    ReaderText.Text(
                                        markdownParser.parse(alt)
                                    )
                                )
                            }
                        }

                        line == "---" || line == "***" -> readerText.add(ReaderText.Separator)

                        else -> {
                            if (
                                !chapterAdded &&
                                formattedLine.clearAllMarkdown().containsVisibleText() &&
                                includeChapter
                            ) {
                                readerText.add(
                                    0, ReaderText.Chapter(
                                        title = formattedLine.clearAllMarkdown(),
                                        nested = false
                                    )
                                )
                                chapterAdded = true
                            } else if (
                                formattedLine.clearMarkdown().containsVisibleText()
                            ) {
                                readerText.add(
                                    ReaderText.Text(
                                        line = markdownParser.parse(formattedLine)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        return readerText
    }

    /**
     * Getting bitmap from [ZipFile] with compression
     * that depends on the [imageEntry] size.
     */
    private fun ZipFile.getImage(imageEntry: ZipEntry): Bitmap? {
        fun getBitmapFromInputStream(compressionLevel: Int = 1): Bitmap? {
            return getInputStream(imageEntry).use { inputStream ->
                BitmapFactory.decodeStream(
                    inputStream,
                    null,
                    BitmapFactory.Options().apply {
                        inPreferredConfig = Bitmap.Config.RGB_565
                        inSampleSize = compressionLevel
                    }
                )
            }
        }


        val uncompressedBitmap = getBitmapFromInputStream() ?: return null
        return uncompressedBitmap
    }
}
