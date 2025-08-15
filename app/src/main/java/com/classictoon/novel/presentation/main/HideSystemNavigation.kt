package com.classictoon.novel.presentation.main

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun HideSystemNavigation() {
    val context = LocalContext.current
    AndroidView(
        factory = {
            View(context).apply {
                post {
                    val window = (context as? android.app.Activity)?.window
                    window?.let {
                        val controller = WindowInsetsControllerCompat(it, this)
                        controller.hide(WindowInsetsCompat.Type.navigationBars())
                        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    }
                }
            }
        }
    )
}