/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclarite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.core.network

object ApiConfig {
    // Replace with your actual API base URL
    const val BASE_URL = "https://your-api-base-url.com/"
    
    // API endpoints
    const val BOOKS_ENDPOINT = "books"
    const val BOOK_CONTENT_ENDPOINT = "books/{bookId}/content"
    
    // Default pagination
    const val DEFAULT_PAGE_SIZE = 20
    const val DEFAULT_PAGE = 1
    
    // Timeouts
    const val CONNECT_TIMEOUT_SECONDS = 30L
    const val READ_TIMEOUT_SECONDS = 60L
    const val WRITE_TIMEOUT_SECONDS = 30L
}
