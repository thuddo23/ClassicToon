package com.classictoon.novel.presentation.server_book_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File

@Composable
fun ChapterSection(
    bookId: Int,
    bookTitle: String,
    onChapterClick: (Int, String) -> Unit,
    isDownloadingContent: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val folder = context.filesDir.path
    var chapters by remember { mutableStateOf<List<ChapterInfo>>(emptyList()) }
    var isLoadingChapters by remember { mutableStateOf(false) }
    var isContentDownloaded by remember { mutableStateOf(false) }
    
    // Check if content is downloaded
    LaunchedEffect(bookId, bookTitle) {
        // TODO update save real file here
        val fileName = "sample_book.html"
        val file = File("${folder}/books/$fileName")
        isContentDownloaded = file.exists()
        
        if (isContentDownloaded) {
            isLoadingChapters = true
            loadChaptersFromFile(file, onChaptersLoaded = { loadedChapters ->
                chapters = loadedChapters
                isLoadingChapters = false
            })
        }
    }
    
    // Refresh chapters when download is successful
    LaunchedEffect(isDownloadingContent) {
        if (!isDownloadingContent && !isContentDownloaded) {
            // Check again if content was downloaded
            // TODO update save real file here
//            val fileName = "${bookTitle.lowercase().replace(" ", "_")}.html"
            val fileName = "sample_book.html"
            val file = File("${folder}/books/$fileName")
            if (file.exists()) {
                isContentDownloaded = true
                isLoadingChapters = true
                loadChaptersFromFile(file, onChaptersLoaded = { loadedChapters ->
                    chapters = loadedChapters
                    isLoadingChapters = false
                })
            }
        }
    }
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Chapters",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            if (isContentDownloaded) {
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "View chapters",
                    modifier = Modifier.size(21.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        when {
            isDownloadingContent -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Downloading book content...",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
            !isContentDownloaded -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Preparing chapters...",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
            isLoadingChapters -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            chapters.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No chapters found",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(chapters) { chapter ->
                        ChapterCard(
                            chapter = chapter,
                            onClick = { onChapterClick(chapter.index, chapter.title) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChapterCard(
    chapter: ChapterInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = chapter.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            if (chapter.preview.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = chapter.preview,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private suspend fun loadChaptersFromFile(
    file: File,
    onChaptersLoaded: (List<ChapterInfo>) -> Unit
) {
    try {
        val htmlContent = file.readText()
        val document = Jsoup.parse(htmlContent)
        val chapters = extractChaptersFromHtml(document)
        onChaptersLoaded(chapters)
    } catch (e: Exception) {
        e.printStackTrace()
        onChaptersLoaded(emptyList())
    }
}

private fun extractChaptersFromHtml(document: Document): List<ChapterInfo> {
    val chapters = mutableListOf<ChapterInfo>()
    var chapterIndex = 0
    
    // Look for chapter-tab divs
    document.select("div.chapter-tab").forEach { chapterDiv ->
        val titleElement = chapterDiv.selectFirst("h2")
        val title = titleElement?.text() ?: "Chapter ${chapterIndex + 1}"
        
        // Extract preview text (first paragraph)
        val firstParagraph = chapterDiv.selectFirst("p")
        val preview = firstParagraph?.text()?.take(100) ?: ""
        
        chapters.add(
            ChapterInfo(
                index = chapterIndex,
                title = title,
                preview = preview
            )
        )
        chapterIndex++
    }
    
    // If no chapter-tab divs found, look for h2 elements
    if (chapters.isEmpty()) {
        document.select("h2").forEach { h2Element ->
            val title = h2Element.text()
            if (title.contains("Chapter", ignoreCase = true)) {
                // Get next paragraph as preview
                val nextParagraph = h2Element.parent()?.selectFirst("p")
                val preview = nextParagraph?.text()?.take(100) ?: ""
                
                chapters.add(
                    ChapterInfo(
                        index = chapterIndex,
                        title = title,
                        preview = preview
                    )
                )
                chapterIndex++
            }
        }
    }
    
    return chapters
}

data class ChapterInfo(
    val index: Int,
    val title: String,
    val preview: String
)
