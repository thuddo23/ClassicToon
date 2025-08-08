/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.ui.theme

import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.classictoon.novel.R
import com.classictoon.novel.domain.ui.ThemeContrast
import com.classictoon.novel.ui.theme.color.aquaTheme
import com.classictoon.novel.ui.theme.color.blackTheme
import com.classictoon.novel.ui.theme.color.blueTheme
import com.classictoon.novel.ui.theme.color.dynamicTheme
import com.classictoon.novel.ui.theme.color.green2Theme
import com.classictoon.novel.ui.theme.color.greenGrayTheme
import com.classictoon.novel.ui.theme.color.greenTheme
import com.classictoon.novel.ui.theme.color.lavenderTheme
import com.classictoon.novel.ui.theme.color.marshTheme
import com.classictoon.novel.ui.theme.color.pink2Theme
import com.classictoon.novel.ui.theme.color.pinkTheme
import com.classictoon.novel.ui.theme.color.purpleGrayTheme
import com.classictoon.novel.ui.theme.color.purpleTheme
import com.classictoon.novel.ui.theme.color.redGrayTheme
import com.classictoon.novel.ui.theme.color.redTheme
import com.classictoon.novel.ui.theme.color.yellow2Theme
import com.classictoon.novel.ui.theme.color.yellowTheme


@Immutable
enum class Theme(
    val hasThemeContrast: Boolean,
    @StringRes val title: Int
) {
    DYNAMIC(hasThemeContrast = false, title = R.string.dynamic_theme),
    BLUE(hasThemeContrast = true, title = R.string.blue_theme),
    GREEN(hasThemeContrast = true, title = R.string.green_theme),
    GREEN2(hasThemeContrast = false, title = R.string.green2_theme),
    GREEN_GRAY(hasThemeContrast = false, title = R.string.green_gray_theme),
    MARSH(hasThemeContrast = true, title = R.string.marsh_theme),
    RED(hasThemeContrast = true, title = R.string.red_theme),
    RED_GRAY(hasThemeContrast = false, title = R.string.red_gray_theme),
    PURPLE(hasThemeContrast = true, title = R.string.purple_theme),
    PURPLE_GRAY(hasThemeContrast = false, title = R.string.purple_gray_theme),
    LAVENDER(hasThemeContrast = true, title = R.string.lavender_theme),
    PINK(hasThemeContrast = true, title = R.string.pink_theme),
    PINK2(hasThemeContrast = false, title = R.string.pink2_theme),
    YELLOW(hasThemeContrast = true, title = R.string.yellow_theme),
    YELLOW2(hasThemeContrast = false, title = R.string.yellow2_theme),
    AQUA(hasThemeContrast = true, title = R.string.aqua_theme);

    companion object {
        fun entries(): List<Theme> {
            return when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> Theme.entries
                else -> Theme.entries.dropWhile { it == DYNAMIC }
            }
        }
    }
}

/**
 * Converting [String] into [Theme].
 */
fun String.toTheme(): Theme {
    return Theme.valueOf(this)
}

/**
 * Creates a colorscheme based on [Theme].
 *
 * @param theme a [Theme].
 *
 * @return a [ColorScheme].
 */
@Composable
fun colorScheme(
    theme: Theme,
    darkTheme: Boolean,
    isPureDark: Boolean,
    themeContrast: ThemeContrast
): ColorScheme {
    val colorScheme = when (theme) {
        Theme.DYNAMIC -> {
            /* Dynamic Theme */
            dynamicTheme(isDark = darkTheme)
        }

        Theme.BLUE -> {
            /* Blue Theme */
            blueTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.PURPLE -> {
            /* Purple Theme */
            purpleTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.PURPLE_GRAY -> {
            /* Purple Gray Theme */
            purpleGrayTheme(isDark = darkTheme)
        }

        Theme.GREEN -> {
            /* Green Theme */
            greenTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.GREEN2 -> {
            /* Green2 Theme */
            green2Theme(isDark = darkTheme)
        }

        Theme.GREEN_GRAY -> {
            /* Green Gray Theme */
            greenGrayTheme(isDark = darkTheme)
        }

        Theme.MARSH -> {
            /* Marsh Theme */
            marshTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.PINK -> {
            /* Pink Theme */
            pinkTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.PINK2 -> {
            /* Pink2 Theme */
            pink2Theme(isDark = darkTheme)
        }

        Theme.LAVENDER -> {
            /* Lavender Theme */
            lavenderTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.YELLOW -> {
            /* Yellow Theme */
            yellowTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.YELLOW2 -> {
            /* Yellow2 Theme */
            yellow2Theme(isDark = darkTheme)
        }

        Theme.RED -> {
            /* Red Theme */
            redTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.RED_GRAY -> {
            /* Red Gray Theme */
            redGrayTheme(isDark = darkTheme)
        }

        Theme.AQUA -> {
            /* Aqua Theme */
            aquaTheme(isDark = darkTheme, themeContrast = themeContrast)
        }
    }

    return if (isPureDark && darkTheme) {
        blackTheme(initialTheme = colorScheme)
    } else {
        colorScheme
    }
}
