/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.mapper.book

import androidx.core.net.toUri
import com.classictoon.novel.R
import com.classictoon.novel.data.local.dto.BookEntity
import com.classictoon.novel.data.remote.dto.RemoteBookResponse
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.library.category.Category
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

    override suspend fun toBook(remoteBookResponse: RemoteBookResponse): Book {
        return Book(
            id = remoteBookResponse.bookId,
            title = remoteBookResponse.title,
            author = remoteBookResponse.author.let { UIText.StringValue(it) },
            description = remoteBookResponse.description,
            scrollIndex = 0,
            scrollOffset = 0,
            progress = 0f,
            filePath = "",
            lastOpened = null,
            category = Category.OTHER,
            coverImage = remoteBookResponse.cover.toUri(),
        )
    }
}
