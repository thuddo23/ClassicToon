package com.classictoon.novel.data.converter

import androidx.room.TypeConverter
import com.classictoon.novel.domain.library.category.Category

class CategoryConverter {
    @TypeConverter
    fun fromCategorySet(categories: Set<Category>): String {
        return categories.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toCategorySet(data: String): Set<Category> {
        return if (data.isEmpty()) emptySet()
        else data.split(",").map { Category.valueOf(it) }.toSet()
    }
}
