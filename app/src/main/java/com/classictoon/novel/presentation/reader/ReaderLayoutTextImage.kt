/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.reader

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.classictoon.novel.domain.reader.ReaderText
import com.classictoon.novel.domain.util.HorizontalAlignment

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.ReaderLayoutTextImage(
    entry: ReaderText.Image,
    sidePadding: Dp,
    imagesCornersRoundness: Dp,
    imagesAlignment: HorizontalAlignment,
    imagesWidth: Float,
    imagesColorEffects: ColorFilter?
) {
    Box(
        modifier = Modifier
            .animateItemPlacement(
                animationSpec = tween(durationMillis = 300)
            )
            .padding(horizontal = sidePadding)
            .fillMaxWidth(),
        contentAlignment = imagesAlignment.alignment
    ) {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(imagesCornersRoundness))
                .fillMaxWidth(imagesWidth),
            bitmap = entry.imageBitmap,
            contentDescription = null,
            colorFilter = imagesColorEffects,
            contentScale = ContentScale.FillWidth
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.ReaderLayoutTextRemoteImage(
    entry: ReaderText.RemoteImage,
    sidePadding: Dp,
    imagesCornersRoundness: Dp,
    imagesAlignment: HorizontalAlignment,
    imagesWidth: Float,
    imagesColorEffects: ColorFilter?
) {
    Box(
        modifier = Modifier
            .animateItemPlacement(
                animationSpec = tween(durationMillis = 300)
            )
            .padding(horizontal = sidePadding)
            .fillMaxWidth(),
        contentAlignment = imagesAlignment.alignment
    ) {
        AsyncImage(
            model = entry.url,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(imagesCornersRoundness))
                .fillMaxWidth(imagesWidth),
            colorFilter = imagesColorEffects,
            contentScale = ContentScale.FillWidth,
            // Enhanced loading and error handling
            onLoading = { 
                // You can add a loading placeholder here
                // For now, the AsyncImage will show a loading state automatically
            },
            onError = { 
                // You can add an error placeholder here
                // For now, the AsyncImage will show an error state automatically
            }
        )
    }
}
