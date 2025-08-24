/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.reader

import androidx.compose.runtime.Composable
import com.classictoon.novel.domain.reader.ReaderText.Chapter
import com.classictoon.novel.domain.util.Drawer
import com.classictoon.novel.ui.reader.ReaderEvent
import com.classictoon.novel.ui.reader.ReaderScreen

@Composable
fun ReaderDrawer(
    drawer: Drawer?,
    chapters: List<Chapter>,
    currentChapter: Chapter?,
    currentChapterProgress: Float,
    scrollToChapter: (ReaderEvent.OnScrollToChapter) -> Unit,
    dismissDrawer: (ReaderEvent.OnDismissDrawer) -> Unit
) {
    ReaderChaptersDrawer(
        show = drawer == ReaderScreen.CHAPTERS_DRAWER,
        chapters = chapters,
        currentChapter = currentChapter,
        currentChapterProgress = currentChapterProgress,
        scrollToChapter = scrollToChapter,
        dismissDrawer = dismissDrawer
    )
}
