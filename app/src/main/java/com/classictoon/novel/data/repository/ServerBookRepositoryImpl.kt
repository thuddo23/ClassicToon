/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.data.repository

import com.classictoon.novel.data.mapper.book.BookMapper
import com.classictoon.novel.data.remote.ApiService
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.repository.ServerBookRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerBookRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val bookMapper: BookMapper
) : ServerBookRepository {

    // Feed methods
    override suspend fun getTrendingBooks(limit: Int): List<Book> {
        return try {
            val response = apiService.getTrendingBooks(limit)
            response.map { bookMapper.toBook(it) }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTopPicks(limit: Int): List<Book> {
        return try {
            val response = apiService.getTopPicks(limit)
            response.map { bookMapper.toBook(it.book) }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCategoryRanking(category: String, period: String): List<Book> {
        return try {
            val response = apiService.getCategoryRanking(category, period)
            response.items.map { bookMapper.toBook(it.book) }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getNewestBooks(limit: Int): List<Book> {
        return try {
            val response = apiService.getNewestBooks(limit)
            response.map { bookMapper.toBook(it) }
        } catch (e: Exception) {
            throw e
        }
    }

    // Discovery methods
    override suspend fun getBooks(
        page: Int,
        limit: Int,
        searchQuery: String?,
        category: String?,
        type: String?,
        sort: String?
    ): List<Book> {
        return try {
            val response = apiService.getBooks(page, limit, searchQuery, category, type, sort)
            response.items.map { bookMapper.toBook(it) }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getBookById(bookId: Int): Book? {
        return try {
            val response = apiService.getBookDetail(bookId)
            bookMapper.toBook(response)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getBookDetail(bookId: Int): Book? {
        return getBookById(bookId) // For now, use the same implementation
    }

    override suspend fun getBookContent(bookId: Int): String {
        return try {
            """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>The Hobbit - Sample Book</title>
    <style>
        body {
            font-family: 'Georgia', serif;
            line-height: 1.6;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f9f6f1;
            color: #2c2c2c;
        }
        
        .book-container {
            background: white;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        
        h1 {
            text-align: center;
            color: #8b4513;
            font-size: 2.5em;
            margin-bottom: 30px;
            border-bottom: 3px solid #d2691e;
            padding-bottom: 10px;
        }
        
        .chapter-tab {
            margin-bottom: 40px;
            padding: 20px;
            background: #fafafa;
            border-left: 4px solid #d2691e;
            border-radius: 4px;
        }
        
        h2 {
            color: #8b4513;
            font-size: 1.8em;
            margin-bottom: 20px;
        }
        
        p {
            text-align: justify;
            margin-bottom: 15px;
            font-size: 1.1em;
        }
        
        img {
            max-width: 100%;
            height: auto;
            display: block;
            margin: 20px auto;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        
        .book-info {
            background: #fff3cd;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 30px;
            border-left: 4px solid #ffc107;
        }
        
        .author {
            font-style: italic;
            color: #666;
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="book-container">
        <h1>The Hobbit</h1>
        <div class="author">by J.R.R. Tolkien</div>
        
        <div class="book-info">
            <strong>About this book:</strong> This is a sample HTML book demonstrating the book server's HTML book functionality. 
            The content below is from "The Hobbit" by J.R.R. Tolkien.
        </div>
        
        <div class="chapter-tab">
            <h2>Chapter 1: An Unexpected Party</h2>
            <p>In a hole in the ground there lived a hobbit. Not a nasty, dirty, wet hole, filled with the ends of worms and an oozy smell, nor yet a dry, bare, sandy hole with nothing in it to sit down on or to eat: it was a hobbit-hole, and that means comfort.</p>

            <img src="https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=300&fit=crop" 
                 alt="Fantasy landscape with mountains and forest" />

            <p>It had a perfectly round door like a porthole, painted green, with a shiny yellow brass knob in the exact middle. The door opened on to a tube-shaped hall like a tunnel: a very comfortable tunnel without smoke, with panelled walls, and floors tiled and carpeted, provided with polished chairs, and lots and lots of pegs for hats and coats—the hobbit was fond of visitors.</p>
            
            <p>The tunnel wound on and on, going fairly but not quite straight into the side of the hill—The Hill, as all the people for many miles round called it—and many little round doors opened out of it, first on one side and then on another. No going upstairs for the hobbit: bedrooms, bathrooms, cellars, pantries (lots of these), wardrobes (he had whole rooms devoted to clothes), kitchens, dining-rooms, all were on the same floor, and indeed on the same passage.</p>
        </div>
        
        <div class="chapter-tab">
            <h2>Chapter 2: Roast Mutton</h2>
            <p>Up jumped Bilbo, and taking the dwarves at their word that they would come back later, he ran along the passage, very angry, and altogether confused and bewuthered—this was the most awkward Wednesday he ever remembered.</p>
            
            <p>He pulled the bell with all his might, and then he pulled it again. "Ring for the elves!" he shouted. "Ring for the elves!" and he kept on ringing.</p>
            
            <p>But the elves were not coming. They were not coming because they were not there. They were not there because they had gone away. They had gone away because they had been driven away by the goblins. The goblins had come down from the mountains and had taken the valley, and the elves had fled.</p>
        </div>
        
        <div class="chapter-tab">
            <h2>Chapter 3: A Short Rest</h2>
            <p>They walked in single file. The entrance to the path was like a sort of arch leading into a gloomy tunnel made by two great trees that leant together, too old and strangled with ivy and hung with lichen to bear more than a few blackened leaves.</p>
            
            <p>The path itself was narrow and wound in and out among the trunks. Soon the light at the gate was like a little bright hole far behind, and the quiet was so deep that their feet seemed to thump along while all the trees leaned over them and listened.</p>
        </div>
        
        <div class="book-info">
            <strong>End of Sample:</strong> This concludes the sample content from "The Hobbit". 
            In a real implementation, this would be a complete HTML book with navigation, 
            table of contents, and additional chapters.
        </div>
    </div>
</body>
</html>
"""
        } catch (e: Exception) {
            throw e
        }
    }

    // Legacy methods for backward compatibility
    override suspend fun searchBooks(
        query: String,
        page: Int,
        limit: Int
    ): List<Book> {
        return getBooks(page, limit, query, null, null, null)
    }

    override suspend fun getBooksByGenre(
        genre: String,
        page: Int,
        limit: Int
    ): List<Book> {
        return getBooks(page, limit, null, genre, null, null)
    }
}