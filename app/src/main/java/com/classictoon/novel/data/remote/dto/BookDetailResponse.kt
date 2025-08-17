/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclarite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BookDetailResponse(
    val id: Int,
    val title: String,
    val type: String,
    val description: String,
    @SerializedName("cover_url")
    val coverUrl: String,
    val language: String,
    val authors: List<String>,
    val categories: List<String>,
    val tags: List<String>,
    @SerializedName("published_at")
    val publishedAt: String? = null,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val metrics: BookMetrics,
    val source: BookSource? = null
)

data class BookSource(
    val id: Int,
    @SerializedName("bookId")
    val bookId: Int,
    val url: String,
    @SerializedName("sizeBytes")
    val sizeBytes: Long? = null
)
