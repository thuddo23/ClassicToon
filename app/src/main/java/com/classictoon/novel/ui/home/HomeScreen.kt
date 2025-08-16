/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.classictoon.novel.domain.navigator.Screen
import com.classictoon.novel.presentation.home.CategorySection
import com.classictoon.novel.presentation.home.HeaderSection
import com.classictoon.novel.presentation.home.NewestArrivalsSection
import com.classictoon.novel.presentation.home.TopPicksSection
import com.classictoon.novel.presentation.home.TopSeriesSection
import com.classictoon.novel.presentation.home.TrendingSection
import com.classictoon.novel.presentation.navigator.LocalNavigator
import com.classictoon.novel.presentation.server_book_detail.ServerBookDetailScreen

object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        HomeScreenContent(
            onBookClick = { bookId ->
                navigator.push(ServerBookDetailScreen(bookId))
            }
        )
    }
}

@Composable
fun HomeScreenContent(
    onBookClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedCategory by remember { mutableStateOf("Fantasy") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.White
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = uiState.error?:"Error loading content",
                        color = Color.Red
                    )
                    Button(
                        onClick = { viewModel.loadHomeFeed() }
                    ) {
                        Text("Retry")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
            ) {
                item {
                    HeaderSection()
                }

                item {
                    TrendingSection(
                        trendingBooks = uiState.trendingBooks,
                        onBookClick = onBookClick
                    )
                }

                item {
                    TopPicksSection(
                        topPicks = uiState.topPicks,
                        onBookClick = onBookClick
                    )
                }

                item {
                    CategorySection(
                        selectedCategory = selectedCategory,
                        onCategorySelected = { selectedCategory = it }
                    )
                }

                item {
                    TopSeriesSection()
                }

                item {
                    NewestArrivalsSection(
                        newestBooks = uiState.newestBooks,
                        onBookClick = onBookClick
                    )
                }
            }
        }
    }
}
