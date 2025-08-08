/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.mapper.book

import com.classictoon.novel.data.local.dto.BookEntity
import com.classictoon.novel.domain.library.book.Book

interface BookMapper {
    suspend fun toBookEntity(book: Book): BookEntity

    suspend fun toBook(bookEntity: BookEntity): Book
}
