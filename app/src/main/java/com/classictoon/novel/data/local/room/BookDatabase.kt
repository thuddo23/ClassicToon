/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.local.room

import android.app.Application
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.DeleteTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.classictoon.novel.data.local.dto.BookEntity
import com.classictoon.novel.data.local.dto.ColorPresetEntity
import com.classictoon.novel.data.local.dto.HistoryEntity
import java.io.File

@Database(
    entities = [
        BookEntity::class,
        HistoryEntity::class,
        ColorPresetEntity::class,
    ],
    version = 10,
    autoMigrations = [
        AutoMigration(1, 2),
        AutoMigration(2, 3),
        AutoMigration(3, 4, spec = DatabaseHelper.MIGRATION_3_4::class),
        AutoMigration(4, 5),
        AutoMigration(5, 6),
        AutoMigration(6, 7),
        AutoMigration(7, 8, spec = DatabaseHelper.MIGRATION_7_8::class),
        AutoMigration(8, 9, spec = DatabaseHelper.MIGRATION_8_9::class),
        AutoMigration(9, 10, spec = DatabaseHelper.MIGRATION_9_10::class),
    ],
    exportSchema = true
)
abstract class BookDatabase : RoomDatabase() {
    abstract val dao: BookDao
}

@Suppress("ClassName")
object DatabaseHelper {

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS `LanguageHistoryEntity` (" +
                        "`languageCode` TEXT NOT NULL," +
                        " `order` INTEGER NOT NULL," +
                        " PRIMARY KEY(`languageCode`)" +
                        ")"
            )
        }
    }

    @DeleteColumn("BookEntity", "enableTranslator")
    @DeleteColumn("BookEntity", "translateFrom")
    @DeleteColumn("BookEntity", "translateTo")
    @DeleteColumn("BookEntity", "doubleClickTranslation")
    @DeleteColumn("BookEntity", "translateWhenOpen")
    @DeleteTable("LanguageHistoryEntity")
    class MIGRATION_3_4 : AutoMigrationSpec

    val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS `ColorPresetEntity` (" +
                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "`name` TEXT, " +
                        "`backgroundColor` INTEGER NOT NULL, " +
                        "`fontColor` INTEGER NOT NULL, " +
                        "`isSelected` INTEGER NOT NULL, " +
                        "`order` INTEGER NOT NULL" +
                        ")"
            )
        }
    }

    val MIGRATION_5_6 = object : Migration(5, 6) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS `FavoriteDirectoryEntity` (" +
                        "`path` TEXT NOT NULL, " +
                        "PRIMARY KEY(`path`)" +
                        ")"
            )
        }
    }

    @DeleteColumn("BookEntity", "textPath")
    @DeleteColumn("BookEntity", "chapters")
    class MIGRATION_7_8 : AutoMigrationSpec {
        companion object {
            /**
             * Along with textPath deletion,
             * books directory with text does not
             * serve any purpose.
             */
            fun removeBooksDir(application: Application) {
                val booksDir = File(application.filesDir, "books")

                if (booksDir.exists()) {
                    booksDir.deleteRecursively()
                }
            }
        }
    }

    @DeleteTable("FavoriteDirectoryEntity")
    class MIGRATION_8_9 : AutoMigrationSpec

    class MIGRATION_9_10 : AutoMigrationSpec {
        companion object {
            /**
             * Migration to handle category changes from reading status to genres.
             * Converts old category values to new genre values.
             */
            fun migrateCategories(db: SupportSQLiteDatabase) {
                // Convert old reading status categories to new genre categories
                // READING -> FANTASY (default for most books)
                db.execSQL("UPDATE BookEntity SET category = 'FANTASY' WHERE category = 'READING'")
                // ALREADY_READ -> ROMANCE (for completed books)
                db.execSQL("UPDATE BookEntity SET category = 'ROMANCE' WHERE category = 'ALREADY_READ'")
                // PLANNING -> ACTION (for planned books)
                db.execSQL("UPDATE BookEntity SET category = 'ACTION' WHERE category = 'PLANNING'")
                // DROPPED -> THRILLER (for abandoned books)
                db.execSQL("UPDATE BookEntity SET category = 'THRILLER' WHERE category = 'DROPPED'")
            }
        }
    }
}
