/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.settings.reader.chapters.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.classictoon.novel.R
import com.classictoon.novel.domain.reader.ReaderTextAlignment
import com.classictoon.novel.domain.ui.ButtonItem
import com.classictoon.novel.presentation.core.components.settings.SegmentedButtonWithTitle
import com.classictoon.novel.ui.main.MainEvent
import com.classictoon.novel.ui.main.MainModel

@Composable
fun ChapterTitleAlignmentOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    SegmentedButtonWithTitle(
        title = stringResource(id = R.string.chapter_title_alignment_option),
        buttons = ReaderTextAlignment.entries.map {
            ButtonItem(
                id = it.toString(),
                title = when (it) {
                    ReaderTextAlignment.START -> stringResource(id = R.string.alignment_start)
                    ReaderTextAlignment.JUSTIFY -> stringResource(id = R.string.alignment_justify)
                    ReaderTextAlignment.CENTER -> stringResource(id = R.string.alignment_center)
                    ReaderTextAlignment.END -> stringResource(id = R.string.alignment_end)
                },
                textStyle = MaterialTheme.typography.labelLarge,
                selected = it == state.value.chapterTitleAlignment
            )
        },
        onClick = {
            mainModel.onEvent(
                MainEvent.OnChangeChapterTitleAlignment(
                    it.id
                )
            )
        }
    )
}
