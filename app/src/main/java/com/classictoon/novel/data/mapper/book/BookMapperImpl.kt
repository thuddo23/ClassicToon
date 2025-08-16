/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.mapper.book

import androidx.core.net.toUri
import com.classictoon.novel.R
import com.classictoon.novel.data.local.dto.BookEntity
import com.classictoon.novel.data.remote.dto.BookListResponse
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.library.category.Category
import com.classictoon.novel.domain.library.category.DEFAULT_CATEGORY
import com.classictoon.novel.domain.ui.UIText
import javax.inject.Inject

class BookMapperImpl @Inject constructor() : BookMapper {
    override suspend fun toBookEntity(book: Book): BookEntity {
        return BookEntity(
            id = book.id,
            title = book.title,
            filePath = book.filePath,
            scrollIndex = book.scrollIndex,
            scrollOffset = book.scrollOffset,
            progress = book.progress,
            author = book.author.getAsString(),
            description = book.description,
            image = book.coverImage?.toString(),
            category = book.category,
        )
    }

    override suspend fun toBook(bookEntity: BookEntity): Book {
        return Book(
            id = bookEntity.id,
            title = bookEntity.title,
            author = bookEntity.author?.let { UIText.StringValue(it) } ?: UIText.StringResource(
                R.string.unknown_author
            ),
            description = bookEntity.description,
            scrollIndex = bookEntity.scrollIndex,
            scrollOffset = bookEntity.scrollOffset,
            progress = bookEntity.progress,
            filePath = bookEntity.filePath,
            lastOpened = null,
            category = bookEntity.category,
            coverImage = bookEntity.image?.toUri()
        )
    }

    override suspend fun toBook(remoteBookResponse: BookListResponse): Book {
        return Book(
            id = remoteBookResponse.id.hashCode(), // Convert string ID to int for local storage
            title = remoteBookResponse.title,
            author = remoteBookResponse.authors.firstOrNull()?.let { UIText.StringValue(it) } 
                ?: UIText.StringResource(R.string.unknown_author),
            description = remoteBookResponse.description,
            scrollIndex = 0,
            scrollOffset = 0,
            progress = 0f,
            filePath = "",
            lastOpened = null,
            category = mapRemoteCategoriesToLocal(remoteBookResponse.categories),
            coverImage = remoteBookResponse.coverUrl.toUri(),
        )
    }
    
    private fun mapRemoteCategoriesToLocal(remoteCategories: List<String>): Set<Category> {
        if (remoteCategories.isEmpty()) return DEFAULT_CATEGORY
        
        val mappedCategories = remoteCategories.mapNotNull { remoteCategory ->
            when (remoteCategory.lowercase()) {
                "fantasy" -> Category.FANTASY
                "romance" -> Category.ROMANCE
                "action" -> Category.ACTION
                "thriller" -> Category.THRILLER
                "comedy" -> Category.COMEDY
                "drama" -> Category.DRAMA
                "mystery" -> Category.MYSTERY
                "science-fiction" -> Category.SCIENCE_FICTION
                "adventure" -> Category.ACTION
                "classic" -> Category.ROMANCE
                "biography" -> Category.DRAMA
                else -> null
            }
        }.toSet()
        
        return mappedCategories.ifEmpty { DEFAULT_CATEGORY }
    }
}
