/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.crash

import android.content.ClipData
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import com.classictoon.novel.R
import com.classictoon.novel.core.crash.CrashUtils
import com.classictoon.novel.domain.navigator.Screen
import com.classictoon.novel.presentation.core.util.LocalActivity
import com.classictoon.novel.presentation.core.util.launchActivity
import com.classictoon.novel.presentation.core.util.showToast
import com.classictoon.novel.presentation.crash.CrashContent

@Parcelize
object CrashScreen : Screen, Parcelable {

    @Composable
    override fun Content() {
        val activity = LocalActivity.current
        val clipboard = LocalClipboardManager.current
        val scope = rememberCoroutineScope()

        val crashLog = remember {
            CrashUtils.getCrashLog(activity)?.trimIndent()
        }

        CrashContent(
            crashLog = crashLog,
            copy = {
                if (crashLog.isNullOrBlank()) return@CrashContent
                scope.launch(Dispatchers.Main) {
                    clipboard.setClip(
                        ClipEntry(ClipData.newPlainText("Crash", crashLog))
                    )

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                        activity.getString(R.string.copied)
                            .showToast(context = activity, longToast = false)
                    }
                }
            },
            report = {
                Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_SUBJECT,
                        activity.getString(R.string.app_name)
                    )
                    putExtra(
                        Intent.EXTRA_TEXT,
                        crashLog
                    )
                }.launchActivity(
                    activity = activity,
                    createChooser = true,
                    success = {
                        return@CrashContent
                    }
                )

                activity.getString(R.string.error_no_share_app)
                    .showToast(context = activity, longToast = false)
            }
        )
    }
}
