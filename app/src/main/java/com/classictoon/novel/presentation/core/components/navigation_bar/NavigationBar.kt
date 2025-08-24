/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.core.components.navigation_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.classictoon.novel.domain.navigator.NavigatorItem
import com.classictoon.novel.presentation.navigator.LocalNavigator

@Composable
fun NavigationBar(tabs: List<NavigatorItem>) {
    val navigator = LocalNavigator.current
    val lastItem = navigator.lastItem.collectAsStateWithLifecycle()

    val currentTab = remember { mutableStateOf(lastItem.value) }
    LaunchedEffect(lastItem.value) {
        if (tabs.any { it.screen::class == lastItem.value::class }) {
            currentTab.value = lastItem.value
        }
    }

    Column {
        NavigationBar(
            windowInsets = WindowInsets(0, 0, 0, 0)
        ) {
            tabs.forEach { tab ->
                NavigationBarItem(
                    item = tab,
                    isSelected = currentTab.value::class == tab.screen::class
                ) {
                    navigator.push(tab.screen)
                }
            }
        }
        // Advertisement space
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                .height(61.dp)
                .background(Color(0xFF374151))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Advertisement Space",
                color = Color(0xFF9CA3AF),
                fontSize = 14.sp
            )
        }
    }
}
