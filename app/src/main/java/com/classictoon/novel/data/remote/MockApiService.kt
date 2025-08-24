///*
// * ClassicToon — free and open-source Material You eBook reader.
// * Copyright (C) 2024-2025 Acclorite
// * SPDX-License-Identifier: GPL-3.0-only
// */
//
//package com.classictoon.novel.data.remote
//
//import com.classictoon.novel.data.remote.dto.*
//import kotlinx.coroutines.delay
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class MockApiService @Inject constructor() {
//
//    private val mockBooks = generateMockBooks()
//    private val mockTopPicks = generateMockTopPicks()
//    private val mockRankings = generateMockRankings()
//
//    // Feed endpoints
//    suspend fun getTrendingBooks(limit: Int = 20): List<BookListResponse> {
//        delay(500)
//        val items = mockBooks.take(limit).map { book ->
//            BookListResponse(
//                id = book.id,
//                title = book.title,
//                type = book.type,
//                coverUrl = book.coverUrl,
//                categories = book.categories,
//                publishedAt = book.publishedAt,
//                metrics = book.metrics
//            )
//        }
//        return items
//    }
//
//    suspend fun getTopPicks(limit: Int = 20): TopPicksResponse {
//        delay(400)
//        val items = mockTopPicks.take(limit).map { pick ->
//            TopPickItem(
//                book = BookListResponse(
//                    id = pick.book.id,
//                    title = pick.book.title,
//                    type = pick.book.type,
//                    coverUrl = pick.book.coverUrl,
//                    categories = pick.book.categories,
//                    publishedAt = pick.book.publishedAt,
//                    metrics = pick.book.metrics
//                ),
//                rank = pick.rank,
//                note = pick.note
//            )
//        }
//        return TopPicksResponse(items = items)
//    }
//
//    suspend fun getCategoryRanking(category: String, period: String = "weekly"): RankingResponse {
//        delay(450)
//        val ranking = mockRankings.find { it.category == category } ?: mockRankings.first()
//        val items = ranking.items.take(20).map { item ->
//            RankingItem(
//                book = BookListResponse(
//                    id = item.book.id,
//                    title = item.book.title,
//                    coverUrl = item.book.coverUrl
//                ),
//                score = item.score
//            )
//        }
//        return RankingResponse(
//            category = category,
//            period = period,
//            generatedAt = "2024-01-01T00:00:00Z",
//            items = items
//        )
//    }
//
//    suspend fun getNewestBooks(limit: Int = 20): List<BookListResponse> {
//        delay(350)
//        val sortedBooks = mockBooks.sortedByDescending { it.publishedAt }
//        val items = sortedBooks.take(limit).map { book ->
//            BookListResponse(
//                id = book.id,
//                title = book.title,
//                type = book.type,
//                coverUrl = book.coverUrl,
//                categories = book.categories,
//                publishedAt = book.publishedAt,
//                metrics = book.metrics
//            )
//        }
//        return items
//    }
//
//    // Discovery endpoints
//    suspend fun getBooks(
//        page: Int = 1,
//        limit: Int = 20,
//        searchQuery: String? = null,
//        category: String? = null,
//        type: String? = null,
//        sort: String? = null
//    ): BookSearchResponse {
//        delay(500)
//
//        var filteredBooks = mockBooks
//
//        // Apply search filter
//        if (!searchQuery.isNullOrBlank()) {
//            filteredBooks = filteredBooks.filter { book ->
//                book.title.contains(searchQuery, ignoreCase = true)
//            }
//        }
//
//        // Apply category filter
//        if (!category.isNullOrBlank()) {
//            filteredBooks = filteredBooks.filter { category in it.categories }
//        }
//
//        // Apply type filter
//        if (!type.isNullOrBlank()) {
//            filteredBooks = filteredBooks.filter { it.type == type }
//        }
//
//        // Apply sorting
//        filteredBooks = when (sort) {
//            "trending" -> filteredBooks.sortedByDescending { it.metrics?.reads }
//            "newest" -> filteredBooks.sortedByDescending { it.publishedAt }
//            "rating" -> filteredBooks.sortedByDescending { it.metrics?.ratingAvg ?: 0.0 }
//            else -> filteredBooks
//        }
//
//        // Apply pagination
//        val startIndex = (page - 1) * limit
//        val endIndex = minOf(startIndex + limit, filteredBooks.size)
//
//        val items = if (startIndex < filteredBooks.size) {
//            filteredBooks.subList(startIndex, endIndex).map { book ->
//                FeedBookItem(
//                    id = book.id,
//                    title = book.title,
//                    type = book.type,
//                    coverUrl = book.coverUrl,
//                    categories = book.categories,
//                    tags = book.tags,
//                    publishedAt = book.publishedAt,
//                    metrics = book.metrics
//                )
//            }
//        } else {
//            emptyList()
//        }
//
//        return Book(
//            items = items,
//            page = page,
//            total = filteredBooks.size
//        )
//    }
//
//    suspend fun getBookDetail(bookId: Int): RemoteBookDetailResponse? {
//        delay(300)
//        return mockBooks.find { it.id == bookId }?.let { book ->
//            RemoteBookDetailResponse(
//                id = book.id,
//                title = book.title,
//                type = book.type,
//                description = book.description,
//                coverUrl = book.coverUrl,
//                authors = book.authors,
//                categories = book.categories,
//                tags = book.tags,
//                language = book.language,
//                publishedAt = book.publishedAt,
//                createdAt = book.createdAt,
//                updatedAt = book.updatedAt,
//                metrics = book.metrics,
//                source = BookSource(
//                    url = "https://remote.example.com/${book.id}.json",
//                    sizeBytes = 1024000L
//                )
//            )
//        }
//    }
//
//    suspend fun getBookContent(bookId: Int): String {
//        delay(400)
//        val book = mockBooks.find { it.id == bookId }
//        val isHtmlBook = book?.type == "html"
//        val hasChapters = book?.tags?.contains("chapter") == true
//
//        return if (isHtmlBook && hasChapters) {
//            generateChapteredHtmlContent(book)
//        } else {
//            generateSimpleHtmlContent(book)
//        }
//    }
//
//    private fun generateMockBooks(): List<BookListResponse> {
//        val categories = listOf("fantasy", "romance", "mystery", "science-fiction", "thriller", "classic", "adventure", "drama", "comedy", "biography")
//        val authors = listOf(
//            "Jane Austen", "Charles Dickens", "Mark Twain", "Virginia Woolf", "F. Scott Fitzgerald",
//            "Ernest Hemingway", "George Orwell", "J.R.R. Tolkien", "Agatha Christie", "Stephen King",
//            "J.K. Rowling", "Dan Brown", "John Grisham", "Nora Roberts", "James Patterson",
//            "Suzanne Collins", "Veronica Roth", "Stephanie Meyer", "E.L. James", "Gillian Flynn"
//        )
//
//        return (0 until 50).map { index ->
//            val isHtml = index % 3 == 0 // Every 3rd book is HTML
//            val hasChapters = isHtml && index % 2 == 0 // Some HTML books have chapters
//
//            BookListResponse(
//                id = if (isHtml) "ill_${index.toString().padStart(2, '0')}" else "pub_${index.toString().padStart(2, '0')}",
//                title = "The Amazing Story of Book $index",
//                type = if (isHtml) "html" else "epub",
//                coverUrl = "https://picsum.photos/300/400?random=$index",
//                description = "This is the description for this book which index is $index\nBest seller 2025",
//                authors = listOf(authors[index % authors.size]),
//                categories = listOf(categories[index % categories.size], categories[(index + 1) % categories.size]),
//                tags = if (hasChapters) listOf("adventure", "chapter") else listOf("classic", "literature"),
//                language = "en",
//                publishedAt = "2024-01-${(index % 28 + 1).toString().padStart(2, '0')}T00:00:00Z",
//                createdAt = "2024-01-01T00:00:00Z",
//                updatedAt = "2024-01-01T00:00:00Z",
//                metrics = BookMetrics(
//                    reads = 1000 + (index * 100) % 10000,
//                    likes = 50 + (index * 10) % 500,
//                    ratingAvg = 3.5 + (index % 15) * 0.1
//                )
//            )
//        }
//    }
//
//    private fun generateMockTopPicks(): List<TopPickItem> {
//        return mockBooks.take(10).mapIndexed { index, book ->
//            TopPickItem(
//                book = BookListResponse(
//                    id = book.id,
//                    title = book.title,
//                    coverUrl = book.coverUrl
//                ),
//                rank = index + 1,
//                note = when (index) {
//                    0 -> "Staff pick"
//                    1 -> "Editor's choice"
//                    2 -> "Must read"
//                    else -> "Recommended"
//                }
//            )
//        }
//    }
//
//    private fun generateMockRankings(): List<RankingResponse> {
//        val categories = listOf("fantasy", "romance", "mystery", "science-fiction")
//        return categories.map { category ->
//            val categoryBooks = mockBooks.filter { category in it.categories }
//                .sortedByDescending { it.metrics.reads }
//                .take(20)
//
//            RankingResponse(
//                category = category,
//                period = "weekly",
//                generatedAt = "2024-01-01T00:00:00Z",
//                items = categoryBooks.mapIndexed { index, book ->
//                    RankingItem(
//                        book = RankingBook(
//                            id = book.id,
//                            title = book.title,
//                            coverUrl = book.coverUrl
//                        ),
//                        score = 100.0 - (index * 2.0)
//                    )
//                }
//            )
//        }
//    }
//
//    private fun generateChapteredHtmlContent(book: BookListResponse?): String {
//        val title = book?.title ?: "Unknown Book"
//        val author = book?.authors?.firstOrNull() ?: "Unknown Author"
//
//        return """
//            <!DOCTYPE html>
//            <html>
//            <head>
//                <meta charset="UTF-8">
//                <title>$title</title>
//                <style>
//                    body { font-family: 'Times New Roman', serif; line-height: 1.6; margin: 40px; }
//                    h1 { color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; }
//                    h2 { color: #34495e; margin-top: 30px; }
//                    p { text-align: justify; margin-bottom: 15px; }
//                    .chapter { margin-bottom: 40px; }
//                    .illustration { text-align: center; margin: 20px 0; }
//                    .illustration img { max-width: 100%; height: auto; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
//                </style>
//            </head>
//            <body>
//                <h1>$title</h1>
//                <h2>By $author</h2>
//
//                <div class="chapter">
//                    <h2>Chapter 1: The Beginning</h2>
//                    <div class="illustration">
//                        <img src="https://picsum.photos/600/400?random=1" alt="Chapter 1 Illustration">
//                    </div>
//                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
//
//                    <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
//                </div>
//
//                <div class="chapter">
//                    <h2>Chapter 2: The Journey Continues</h2>
//                    <div class="illustration">
//                        <img src="https://picsum.photos/600/400?random=2" alt="Chapter 2 Illustration">
//                    </div>
//                    <p>Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.</p>
//                </div>
//
//                <div class="chapter">
//                    <h2>Chapter 3: The Conclusion</h2>
//                    <div class="illustration">
//                        <img src="https://picsum.photos/600/400?random=3" alt="Chapter 3 Illustration">
//                    </div>
//                    <p>Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus.</p>
//                </div>
//            </body>
//            </html>
//        """.trimIndent()
//    }
//
//    private fun generateSimpleHtmlContent(book: BookListResponse?): String {
//        val title = book?.title ?: "Unknown Book"
//        val author = book?.authors?.firstOrNull() ?: "Unknown Author"
//
//        return """
//            <!DOCTYPE html>
//            <html>
//            <head>
//                <meta charset="UTF-8">
//                <title>$title</title>
//                <style>
//                    body { font-family: 'Times New Roman', serif; line-height: 1.6; margin: 40px; }
//                    h1 { color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; }
//                    h2 { color: #34495e; margin-top: 30px; }
//                    p { text-align: justify; margin-bottom: 15px; }
//                </style>
//            </head>
//            <body>
//                <h1>$title</h1>
//                <h2>By $author</h2>
//
//                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
//
//                <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
//
//                <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p>
//            </body>
//            </html>
//        """.trimIndent()
//    }
//}
