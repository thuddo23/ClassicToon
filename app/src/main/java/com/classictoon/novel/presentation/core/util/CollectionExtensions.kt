/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.core.util

fun <T> MutableList<T>.addAll(calculation: () -> List<T>) {
    addAll(calculation())
}
