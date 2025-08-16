/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclarite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.use_case

import android.app.Application
import android.graphics.Bitmap
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.library.book.BookWithCover
import com.classictoon.novel.domain.repository.BookRepository
import com.classictoon.novel.domain.repository.ServerBookRepository
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class DownloadBookUseCase @Inject constructor(
    private val serverBookRepository: ServerBookRepository,
    private val bookRepository: BookRepository,
    private val context: Application,
) {
    
    suspend fun execute(bookId: String): Result<Book> {
        return try {
            // Get book details and content from server
            val serverBook = serverBookRepository.getBookById(bookId)
                ?: return Result.failure(IllegalArgumentException("Book not found"))
            
            val htmlContent = serverBookRepository.getBookContent(bookId)
            
            // Save HTML content to file
            val savedFilePath = saveHtmlContent(serverBook.title, htmlContent)
            
            // Update the BookEntity with the file path
            val updatedBookEntity = serverBook.copy(
                filePath = savedFilePath
            )
            
            // Save book to local database
            // TODO update here
            bookRepository.insertBook(
                BookWithCover(
                    book = updatedBookEntity,
                    coverImage = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565)
                    )
            )
            
            Result.success(updatedBookEntity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun saveHtmlContent(bookTitle: String, htmlContent: String): String {
        val fileName = "${bookTitle.replace(Regex("[^a-zA-Z0-9]"), "_")}_${System.currentTimeMillis()}.html"
        val file = File(context.filesDir, "books/$fileName")
        
        // Create directories if they don't exist
        file.parentFile?.mkdirs()
        
        try {
            FileOutputStream(file).use { fos ->
                fos.write(htmlContent.toByteArray(Charsets.UTF_8))
            }
            return file.absolutePath
        } catch (e: IOException) {
            throw IOException("Failed to save book content: ${e.message}")
        }
    }
}
