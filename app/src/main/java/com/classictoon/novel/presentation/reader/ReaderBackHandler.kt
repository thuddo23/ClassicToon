/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.reader

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.classictoon.novel.presentation.core.util.LocalActivity
import com.classictoon.novel.ui.reader.ReaderEvent

@Composable
fun ReaderBackHandler(
    leave: (ReaderEvent.OnLeave) -> Unit,
    navigateBack: () -> Unit
) {
    val activity = LocalActivity.current

    BackHandler {
        leave(
            ReaderEvent.OnLeave(
                activity = activity,
                navigate = {
                    navigateBack()
                }
            )
        )
    }
}
