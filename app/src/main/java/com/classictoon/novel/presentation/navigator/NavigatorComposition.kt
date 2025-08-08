/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.navigator

import androidx.compose.runtime.compositionLocalOf
import com.classictoon.novel.domain.navigator.Navigator

val LocalNavigator = compositionLocalOf<Navigator> { error("Navigator was not passed.") }
