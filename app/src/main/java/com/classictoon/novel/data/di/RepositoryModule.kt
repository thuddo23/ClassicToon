/*
 * ClassicToon — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.classictoon.novel.BuildConfig
import com.classictoon.novel.data.local.data_store.DataStore
import com.classictoon.novel.data.local.data_store.DataStoreImpl
import com.classictoon.novel.data.mapper.book.BookMapper
import com.classictoon.novel.data.mapper.book.BookMapperImpl
import com.classictoon.novel.data.mapper.color_preset.ColorPresetMapper
import com.classictoon.novel.data.mapper.color_preset.ColorPresetMapperImpl
import com.classictoon.novel.data.mapper.history.HistoryMapper
import com.classictoon.novel.data.mapper.history.HistoryMapperImpl
import com.classictoon.novel.data.parser.FileParser
import com.classictoon.novel.data.parser.FileParserImpl
import com.classictoon.novel.data.parser.TextParser
import com.classictoon.novel.data.parser.TextParserImpl
import com.classictoon.novel.data.repository.BookRepositoryImpl
import com.classictoon.novel.data.repository.ColorPresetRepositoryImpl
import com.classictoon.novel.data.repository.DataStoreRepositoryImpl
import com.classictoon.novel.data.repository.DebugBookRepositoryImpl
import com.classictoon.novel.data.repository.FileSystemRepositoryImpl
import com.classictoon.novel.data.repository.HistoryRepositoryImpl
import com.classictoon.novel.data.repository.PermissionRepositoryImpl
import com.classictoon.novel.data.repository.ServerBookRepositoryImpl
import com.classictoon.novel.domain.repository.BookRepository
import com.classictoon.novel.domain.repository.ColorPresetRepository
import com.classictoon.novel.domain.repository.DataStoreRepository
import com.classictoon.novel.domain.repository.FileSystemRepository
import com.classictoon.novel.domain.repository.HistoryRepository
import com.classictoon.novel.domain.repository.PermissionRepository
import com.classictoon.novel.domain.repository.ServerBookRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindDataStore(
        dataStoreImpl: DataStoreImpl
    ): DataStore

    companion object {
        @Provides
        @Singleton
        fun provideBookRepository(
            bookRepositoryImpl: BookRepositoryImpl,
            debugBookRepositoryImpl: DebugBookRepositoryImpl
        ): BookRepository {
            /*return if (BuildConfig.DEBUG) {
                debugBookRepositoryImpl
            } else {
                bookRepositoryImpl
            }*/
            return bookRepositoryImpl
        }
    }

    @Binds
    @Singleton
    abstract fun bindServerBookRepository(
        serverBookRepositoryImpl: ServerBookRepositoryImpl
    ): ServerBookRepository

    @Binds
    @Singleton
    abstract fun bindHistoryRepository(
        historyRepositoryImpl: HistoryRepositoryImpl
    ): HistoryRepository

    @Binds
    @Singleton
    abstract fun bindColorPresetRepository(
        colorPresetRepositoryImpl: ColorPresetRepositoryImpl
    ): ColorPresetRepository

    @Binds
    @Singleton
    abstract fun bindDataStoreRepository(
        dataStoreRepositoryImpl: DataStoreRepositoryImpl
    ): DataStoreRepository

    @Binds
    @Singleton
    abstract fun bindFileSystemRepository(
        fileSystemRepositoryImpl: FileSystemRepositoryImpl
    ): FileSystemRepository

    @Binds
    @Singleton
    abstract fun bindPermissionRepository(
        permissionRepositoryImpl: PermissionRepositoryImpl
    ): PermissionRepository

    @Binds
    @Singleton
    abstract fun bindBookMapper(
        bookMapperImpl: BookMapperImpl
    ): BookMapper

    @Binds
    @Singleton
    abstract fun bindHistoryMapper(
        historyMapperImpl: HistoryMapperImpl
    ): HistoryMapper

    @Binds
    @Singleton
    abstract fun bindColorPresetMapper(
        colorPresetMapperImpl: ColorPresetMapperImpl
    ): ColorPresetMapper

    @Binds
    @Singleton
    abstract fun bindFileParser(
        fileParserImpl: FileParserImpl
    ): FileParser

    @Binds
    @Singleton
    abstract fun bindTextParser(
        textParserImpl: TextParserImpl
    ): TextParser
}
