/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.splash

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.parcelize.Parcelize
import com.classictoon.novel.R
import com.classictoon.novel.domain.navigator.Screen
import com.classictoon.novel.presentation.navigator.LocalNavigator
import com.classictoon.novel.presentation.server_books.ServerBooksScreen
import com.classictoon.novel.ui.main.MainModel
import com.classictoon.novel.ui.start.StartScreen

@Parcelize
object SplashScreen : Screen, Parcelable {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val mainModel = hiltViewModel<MainModel>()
        
        val mainState = mainModel.state.collectAsStateWithLifecycle()
        val isLoaded = mainModel.isReady.collectAsStateWithLifecycle()

        // Navigate to appropriate screen when app is loaded
        LaunchedEffect(isLoaded.value) {
            if (isLoaded.value) {
                val targetScreen = if (mainState.value.showStartScreen) {
                    StartScreen
                } else {
                    ServerBooksScreen
                }
                navigator.push(targetScreen, saveInBackStack = false)
            }
        }

        // Splash screen UI
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            // Background image placeholder - will be replaced with actual background later
            Image(
                painter = painterResource(id = R.drawable.img_splash), // Placeholder
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                // Welcome text
                Text(
                    text = stringResource(id = R.string.loading_welcome_to),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // App title
                Image(
                    painter = painterResource(id = R.drawable.img_text_classictoon),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Subtitle
                Text(
                    text = stringResource(id = R.string.loading_subtitle),
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                
                Spacer(modifier = Modifier.height(60.dp))
                
                // Loading spinner
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = Color.White,
                    strokeWidth = 3.dp
                )

                Spacer(Modifier.height(60.dp))
            }
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen.Content()
}