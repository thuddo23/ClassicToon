/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclarite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BookListResponse(
    val id: Int,
    val title: String,
    val type: String? = null,
    @SerializedName("cover_url")
    val coverUrl: String? = null,
    val categories: List<String>? = null,
    val metrics: BookMetrics? = null,
    @SerializedName("publishedAt")
    val publishedAt: String? = null
)

data class BookMetrics(
    val reads: Int,
    val likes: Int? = null,
    @SerializedName("rating_avg")
    val ratingAvg: Double? = null
)
