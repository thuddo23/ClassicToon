/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RemoteBookResponse(
    @SerializedName("book_id")
    val bookId: Int,
    val title: String,
    val author: String,
    val description: String,
    @SerializedName("people_read")
    val peopleRead: Int,
    val cover: String,
    val category: String
)