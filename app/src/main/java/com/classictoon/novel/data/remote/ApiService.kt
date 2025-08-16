/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclarite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.remote

import com.classictoon.novel.data.remote.dto.BookListResponse
import com.classictoon.novel.data.remote.dto.BookDetailResponse
import com.classictoon.novel.data.remote.dto.BookSearchResponse
import com.classictoon.novel.data.remote.dto.TopPicksResponse
import com.classictoon.novel.data.remote.dto.RankingResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Body

interface ApiService {
    
    // Feed endpoints - all return BookListResponse for consistency
    @GET("feed/everyone-reading")
    suspend fun getTrendingBooks(
        @Query("limit") limit: Int = 20
    ): List<BookListResponse>
    
    @GET("feed/top-picks")
    suspend fun getTopPicks(
        @Query("limit") limit: Int = 20
    ): TopPicksResponse
    
    @GET("feed/ranking")
    suspend fun getCategoryRanking(
        @Query("category") category: String,
        @Query("period") period: String = "all_time"
    ): RankingResponse
    
    @GET("feed/newest")
    suspend fun getNewestBooks(
        @Query("limit") limit: Int = 20
    ): List<BookListResponse>
    
    // Discovery endpoints - use same BookListResponse for consistency
    @GET("books")
    suspend fun getBooks(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("q") searchQuery: String? = null,
        @Query("category") category: String? = null,
        @Query("type") type: String? = null,
        @Query("sort") sort: String? = null
    ): BookSearchResponse
    
    @GET("books/{bookId}")
    suspend fun getBookDetail(
        @Path("bookId") bookId: Int
    ): BookDetailResponse
    
    // Book content endpoint (kept for backward compatibility)
    @GET("books/{bookId}/content")
    suspend fun getBookContent(
        @Path("bookId") bookId: Int
    ): String
}
