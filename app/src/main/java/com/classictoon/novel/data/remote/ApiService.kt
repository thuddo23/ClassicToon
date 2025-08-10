/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclarite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.remote

import com.classictoon.novel.data.remote.dto.RemoteBookResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    
    @GET("books")
    suspend fun getBooks(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("search") searchQuery: String? = null,
        @Query("category") category: String? = null
    ): List<RemoteBookResponse>
    
    @GET("books/{bookId}/content")
    suspend fun getBookContent(
        @Path("bookId") bookId: String
    ): String
}
