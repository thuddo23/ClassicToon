/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclarite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.remote.dto

data class TopPicksResponse(
    val items: List<TopPickItem>
)

data class TopPickItem(
    val book: BookListResponse,
    val rank: Int,
    val note: String
)
