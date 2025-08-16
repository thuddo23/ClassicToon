/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclarite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RankingResponse(
    val category: String,
    val period: String,
    @SerializedName("generatedAt")
    val generatedAt: String,
    val items: List<RankingItem>
)

data class RankingItem(
    val book: BookListResponse,
    val score: Double
)
