///*
// * Book's Story — free and open-source Material You eBook reader.
// * Copyright (C) 2024-2025 Acclorite
// * SPDX-License-Identifier: GPL-3.0-only
// */
//
//package com.classictoon.novel.presentation.server_book_reader
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Settings
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//
//@Composable
//fun ServerBookReaderScreen(
//    bookId: String,
//    onBackClick: () -> Unit,
//    modifier: Modifier = Modifier,
//    viewModel: ServerBookReaderViewModel = hiltViewModel()
//) {
//    val uiState by viewModel.uiState.collectAsState()
//
//    LaunchedEffect(bookId) {
//        viewModel.loadBookContent(bookId)
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Reading") },
//                navigationIcon = {
//                    IconButton(onClick = onBackClick) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                },
//                actions = {
//                    IconButton(onClick = { /* TODO: Add reading settings */ }) {
//                        Icon(Icons.Default.Settings, contentDescription = "Settings")
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        when {
//            uiState.isLoading -> {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(paddingValues),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            }
//            uiState.error != null -> {
//                ErrorContent(
//                    error = uiState.error!!,
//                    onRetry = { viewModel.loadBookContent(bookId) },
//                    onBackClick = onBackClick
//                )
//            }
//            uiState.content != null -> {
//                BookContent(
//                    content = uiState.content!!,
//                    modifier = Modifier.padding(paddingValues)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun BookContent(
//    content: String,
//    modifier: Modifier = Modifier
//) {
//    val webViewState = rememberWebViewState(
//        data = content,
//        baseUrl = "file:///android_asset/"
//    )
//
//    WebView(
//        state = webViewState,
//        modifier = modifier.fillMaxSize()
//    )
//}
//
//@Composable
//private fun ErrorContent(
//    error: String,
//    onRetry: () -> Unit,
//    onBackClick: () -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "Error",
//            style = MaterialTheme.typography.headlineMedium,
//            color = MaterialTheme.colorScheme.error
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Text(
//            text = error,
//            style = MaterialTheme.typography.bodyMedium,
//            textAlign = TextAlign.Center
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            Button(onClick = onRetry) {
//                Text("Retry")
//            }
//
//            OutlinedButton(onClick = onBackClick) {
//                Text("Back")
//            }
//        }
//    }
//}
