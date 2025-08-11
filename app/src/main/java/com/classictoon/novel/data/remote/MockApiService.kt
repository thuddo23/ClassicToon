/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.remote

import com.classictoon.novel.data.remote.dto.RemoteBookResponse
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockApiService @Inject constructor() {
    
    private val mockBooks = generateMockBooks()
    
    suspend fun getBooks(page: Int, limit: Int, searchQuery: String?, genre: String?): List<RemoteBookResponse> {
        // Simulate network delay
        delay(500)
        
        var filteredBooks = mockBooks
        
        // Apply search filter
        if (!searchQuery.isNullOrBlank()) {
            filteredBooks = filteredBooks.filter { book ->
                book.title.contains(searchQuery, ignoreCase = true) ||
                book.author.contains(searchQuery, ignoreCase = true)
            }
        }
        
        // Apply genre filter
        if (!genre.isNullOrBlank()) {
            filteredBooks = filteredBooks.filter { it.category == genre }
        }
        
        // Apply pagination
        val startIndex = (page - 1) * limit
        val endIndex = minOf(startIndex + limit, filteredBooks.size)
        
        return if (startIndex < filteredBooks.size) {
            filteredBooks.subList(startIndex, endIndex)
        } else {
            emptyList()
        }
    }
    
    suspend fun getBookById(bookId: Int): RemoteBookResponse? {
        delay(300)
        return mockBooks.find { it.bookId == bookId }
    }
    
    suspend fun getBookContent(bookId: Int): String {
        delay(400)
        // Return mock HTML content based on book ID
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>${mockBooks.find { it.bookId == bookId }?.title ?: "Unknown Book"}</title>
                <style>
                    body { font-family: 'Times New Roman', serif; line-height: 1.6; margin: 40px; }
                    h1 { color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; }
                    h2 { color: #34495e; margin-top: 30px; }
                    p { text-align: justify; margin-bottom: 15px; }
                    .chapter { margin-bottom: 40px; }
                </style>
            </head>
            <body>
                <h1>${mockBooks.find { it.bookId == bookId }?.title ?: "Unknown Book"}</h1>
                <h2>By ${mockBooks.find { it.bookId == bookId }?.author ?: "Unknown Author"}</h2>
                
                <div class="chapter">
                    <h2>Chapter 1: The Beginning</h2>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                    
                    <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                    
                    <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p>
                </div>
                
                <div class="chapter">
                    <h2>Chapter 2: The Journey Continues</h2>
                    <p>Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit.</p>
                    
                    <p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga.</p>
                </div>
                
                <div class="chapter">
                    <h2>Chapter 3: The Conclusion</h2>
                    <p>Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus.</p>
                    
                    <p>Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.</p>
                </div>
            </body>
            </html>
        """.trimIndent()
    }
    
    private fun generateMockBooks(): List<RemoteBookResponse> {
        val categories = listOf("Fiction", "Mystery", "Romance", "Science Fiction", "Fantasy", "Thriller", "Historical Fiction", "Biography", "Self-Help", "Poetry")
        val authors = listOf(
            "Jane Austen", "Charles Dickens", "Mark Twain", "Virginia Woolf", "F. Scott Fitzgerald",
            "Ernest Hemingway", "George Orwell", "J.R.R. Tolkien", "Agatha Christie", "Stephen King",
            "J.K. Rowling", "Dan Brown", "John Grisham", "Nora Roberts", "James Patterson",
            "Suzanne Collins", "Veronica Roth", "Stephanie Meyer", "E.L. James", "Gillian Flynn"
        )
        
        return (0 until 50).map { index ->
            RemoteBookResponse(
                bookId = index,
                title = "The Amazing Story of Book $index",
                author = authors[index % authors.size],
                description = "This is the description for this book which index is $index\nBest seller 2025",
                peopleRead = 1000 + (index * 100) % 10000,
                cover = "https://picsum.photos/300/400?random=$index",
                category = categories[index % categories.size]
            )
        }
    }
}
