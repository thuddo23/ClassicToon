/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.use_case.permission

import android.net.Uri
import com.classictoon.novel.domain.repository.PermissionRepository
import javax.inject.Inject

class ReleasePersistableUriPermission @Inject constructor(
    private val repository: PermissionRepository
) {

    suspend fun execute(uri: Uri) {
        repository.releasePersistableUriPermission(uri)
    }
}
