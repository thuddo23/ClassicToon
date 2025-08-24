/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.domain.file

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import com.anggrayudi.storage.file.DocumentFileCompat
import com.anggrayudi.storage.file.getAbsolutePath

object CachedFileCompat {
    fun fromUri(context: Context, uri: Uri, builder: CachedFileBuilder? = null): CachedFile {
        return CachedFile(
            context = context,
            uri = when {
                DocumentsContract.isDocumentUri(context, uri) -> uri

                DocumentsContract.isTreeUri(uri) -> {
                    DocumentsContract.buildDocumentUriUsingTree(
                        uri,
                        DocumentsContract.getTreeDocumentId(uri)
                    )
                }

                else -> uri
            },
            builder = builder
        )
    }

    fun fromFullPath(
        context: Context,
        path: String,
        builder: CachedFileBuilder? = null
    ): CachedFile? {
        android.util.Log.d("CachedFileCompat", "fromFullPath called with path: $path")
        
        // Check if this is an internal app file path
        if (isInternalAppFile(context, path)) {
            android.util.Log.d("CachedFileCompat", "Detected internal app file: $path")
            return createInternalFileCachedFile(context, path, builder)
        }
        
        android.util.Log.d("CachedFileCompat", "Treating as external file, using SAF: $path")
        // Handle external storage files using SAF
        val uri = try {
            val storageId = DocumentFileCompat.getStorageId(context, path)
            if (storageId.isBlank()) throw NullPointerException("Could not get storageId.")

            val basePath = DocumentFileCompat.getBasePath(context, path)
            if (basePath.isBlank()) throw NullPointerException("Could not get basePath.")

            val parentUri = context.contentResolver.persistedUriPermissions.find {
                try {
                    val persistedUri = DocumentFileCompat.fromUri(context, it.uri)
                    val persistedUriPath = persistedUri?.getAbsolutePath(context)

                    return@find !(persistedUri == null ||
                            !persistedUri.canRead() ||
                            persistedUriPath.isNullOrBlank() ||
                            !path.startsWith(persistedUriPath, ignoreCase = true))
                } catch (e: Exception) {
                    e.printStackTrace()
                    return@find false
                }
            }?.uri
            if (parentUri == null) throw NullPointerException("Could not get parentUri.")

            DocumentsContract.buildDocumentUriUsingTree(parentUri, "$storageId:$basePath")
        } catch (e: Exception) {
            android.util.Log.e("CachedFileCompat", "Error creating SAF URI for path: $path", e)
            e.printStackTrace()
            return null
        }

        val cachedFile = CachedFile(
            context = context,
            uri = when {
                DocumentsContract.isDocumentUri(context, uri) -> uri

                DocumentsContract.isTreeUri(uri) -> {
                    DocumentsContract.buildDocumentUriUsingTree(
                        uri,
                        DocumentsContract.getTreeDocumentId(uri)
                    )
                }

                else -> uri
            },
            builder = builder
        )

        if (!cachedFile.canAccess()) {
            android.util.Log.e("CachedFileCompat", "CachedFile cannot access URI: ${cachedFile.uri}")
            return null
        }
        return cachedFile
    }

    /**
     * Check if the given path is within the app's internal storage
     */
    private fun isInternalAppFile(context: Context, path: String): Boolean {
        val internalFilesDir = context.filesDir.absolutePath
        val internalCacheDir = context.cacheDir.absolutePath
        val internalDataDir = context.dataDir.absolutePath
        
        return path.startsWith(internalFilesDir, ignoreCase = true) ||
               path.startsWith(internalCacheDir, ignoreCase = true) ||
               path.startsWith(internalDataDir, ignoreCase = true)
    }

    /**
     * Create a CachedFile for internal app files that can be accessed directly
     */
    private fun createInternalFileCachedFile(
        context: Context,
        path: String,
        builder: CachedFileBuilder?
    ): CachedFile? {
        val file = java.io.File(path)
        android.util.Log.d("CachedFileCompat", "Creating internal file CachedFile for: $path")
        android.util.Log.d("CachedFileCompat", "File exists: ${file.exists()}, Can read: ${file.canRead()}")
        
        if (!file.exists() || !file.canRead()) {
            android.util.Log.e("CachedFileCompat", "Internal file cannot be accessed: $path")
            return null
        }
        
        android.util.Log.d("CachedFileCompat", "Successfully created internal file CachedFile for: $path")
        return object : CachedFile(
            context = context,
            uri = Uri.fromFile(file),
            builder = builder ?: CachedFileCompat.build(
                name = file.name,
                path = file.absolutePath,
                size = file.length(),
                lastModified = file.lastModified(),
                isDirectory = file.isDirectory
            )
        ) {
            override fun canAccess(): Boolean {
                val canAccess = file.exists() && file.canRead()
                android.util.Log.d("CachedFileCompat", "Internal file canAccess check: $canAccess for $path")
                return canAccess
            }
            
            override fun openInputStream(): java.io.InputStream? {
                return try {
                    android.util.Log.d("CachedFileCompat", "Opening input stream for internal file: $path")
                    file.inputStream()
                } catch (e: Exception) {
                    android.util.Log.e("CachedFileCompat", "Failed to open input stream for internal file: $path", e)
                    e.printStackTrace()
                    null
                }
            }
        }
    }

    fun build(
        name: String? = null,
        path: String? = null,
        size: Long? = null,
        lastModified: Long? = null,
        isDirectory: Boolean? = null
    ): CachedFileBuilder {
        return CachedFileBuilder(
            name = name,
            path = path,
            size = size,
            lastModified = lastModified,
            isDirectory = isDirectory
        )
    }
}
