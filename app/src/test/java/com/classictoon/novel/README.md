# Testing Setup for ClassicToon Novel App

This directory contains comprehensive unit tests for the ClassicToon Novel application, including tests for API services, repositories, mappers, and use cases.

## Test Structure

```
app/src/test/java/com/classictoon/novel/
├── data/
│   ├── remote/
│   │   └── ApiServiceTest.kt           # Tests for API service interface
│   ├── repository/
│   │   └── ServerBookRepositoryImplTest.kt  # Tests for repository implementation
│   └── mapper/
│       └── book/
│           └── BookMapperImplTest.kt   # Tests for book mapper
├── domain/
│   └── use_case/
│       ├── GetTrendingBooksUseCaseTest.kt
│       ├── GetTopPicksUseCaseTest.kt
│       └── GetServerBooksUseCaseTest.kt
├── TestConfig.kt                       # Test configuration utilities
├── TestUtils.kt                        # Comprehensive test utilities
└── README.md                           # This file
```

## Dependencies Added

The following testing dependencies have been added to `build.gradle.kts`:

### Core Testing
- **JUnit 4**: `junit:junit:4.13.2` - Core testing framework
- **Kotlin Test**: `kotlin("test")` - Kotlin testing utilities

### Mocking
- **MockK**: `io.mockk:mockk:1.13.8` - Kotlin-first mocking library
- **MockK Android**: `io.mockk:mockk-android:1.13.8` - Android-specific mocking
- **Mockito**: `org.mockito:mockito-core:5.7.0` - Alternative mocking library
- **Mockito Kotlin**: `org.mockito.kotlin:mockito-kotlin:5.2.1` - Kotlin extensions for Mockito

### Coroutines Testing
- **Kotlin Coroutines Test**: `org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3`
- **Turbine**: `app.cash.turbine:turbine:1.0.0` - For testing flows

### Android Testing
- **Robolectric**: `org.robolectric:robolectric:4.11.1` - Android testing framework
- **AndroidX Test**: Core testing utilities for Android
- **Espresso**: UI testing framework

### API Testing
- **MockWebServer**: `com.squareup.okhttp3:mockwebserver:4.12.0` - HTTP server for testing
- **WireMock**: `org.wiremock:wiremock-standalone:3.2.0` - Alternative API mocking

### Assertions
- **Truth**: `com.google.truth:truth:1.1.5` - Fluent assertions
- **JUnit Extensions**: Additional JUnit utilities

### Framework Testing
- **Hilt Testing**: Dagger Hilt testing utilities
- **Room Testing**: Room database testing
- **Arch Core Testing**: Architecture components testing

## Test Utilities

### TestUtils Object

The `TestUtils` object provides factory methods for creating test data:

```kotlin
// Create test BookListResponse
val bookResponse = TestUtils.createTestBookListResponse(
    id = "test_book_1",
    title = "Test Book",
    categories = listOf("fantasy")
)

// Create test Book
val book = TestUtils.createTestBook(
    id = 1,
    title = "Test Book",
    author = "Test Author"
)

// Create multiple test books
val books = TestUtils.createTestBooks(count = 5)

// Create test BookDetailResponse
val detailResponse = TestUtils.createTestBookDetailResponse(
    id = "detail_1",
    title = "Detailed Book"
)
```

### TestExtensions Object

Provides additional utilities for testing:

```kotlin
// Create random test book
val randomBook = TestExtensions.createRandomTestBook()

// Create multiple random books
val randomBooks = TestExtensions.createRandomTestBooks(count = 10)
```

### MainDispatcherRule

A JUnit rule for testing coroutines:

```kotlin
@get:Rule
val mainDispatcherRule = MainDispatcherRule()

@Test
fun `test coroutine function`() = runTest {
    // Your test code here
}
```

## Running Tests

### Run All Tests
```bash
./gradlew test
```

### Run Specific Test Class
```bash
./gradlew test --tests "com.classictoon.novel.data.remote.ApiServiceTest"
```

### Run Tests with Coverage
```bash
./gradlew testDebugUnitTestCoverage
```

### Run Tests in Android Studio
1. Right-click on the test file or directory
2. Select "Run Tests" or "Run Tests with Coverage"

## Test Examples

### API Service Test
```kotlin
@Test
fun `getTrendingBooks should return list of books`() = runTest {
    // Given
    val mockResponse = "[{\"id\":\"book1\",\"title\":\"Test Book\"}]"
    mockWebServer.enqueue(MockResponse().setBody(mockResponse))
    
    // When
    val result = apiService.getTrendingBooks(limit = 1)
    
    // Then
    assertEquals(1, result.size)
    assertEquals("book1", result[0].id)
}
```

### Repository Test
```kotlin
@Test
fun `getTrendingBooks should return mapped books from API`() = runTest {
    // Given
    val mockApiResponse = listOf(TestUtils.createTestBookListResponse())
    val expectedBook = TestUtils.createTestBook()
    
    coEvery { apiService.getTrendingBooks(any()) } returns mockApiResponse
    coEvery { bookMapper.toBook(any<BookListResponse>()) } returns expectedBook
    
    // When
    val result = repository.getTrendingBooks(limit = 1)
    
    // Then
    assertEquals(1, result.size)
    assertEquals(expectedBook, result[0])
}
```

### Use Case Test
```kotlin
@Test
fun `invoke should return trending books from repository`() = runTest {
    // Given
    val expectedBooks = TestUtils.createTestBooks(count = 2)
    coEvery { repository.getTrendingBooks(any()) } returns expectedBooks
    
    // When
    val result = useCase(limit = 2)
    
    // Then
    assertEquals(2, result.size)
    assertEquals(expectedBooks[0], result[0])
}
```

## Best Practices

1. **Use TestUtils**: Always use the provided test utilities to create consistent test data
2. **Mock Dependencies**: Use MockK or Mockito to mock dependencies
3. **Test Coroutines**: Use `runTest` for testing suspend functions
4. **Use Descriptive Test Names**: Use backticks for readable test names
5. **Follow AAA Pattern**: Arrange (Given), Act (When), Assert (Then)
6. **Test Edge Cases**: Include tests for null values, empty lists, and error conditions
7. **Use Test Rules**: Use `MainDispatcherRule` for coroutine testing

## Coverage

The test suite provides comprehensive coverage for:
- ✅ API service interface and endpoints
- ✅ Repository implementations
- ✅ Data mappers
- ✅ Use cases
- ✅ Error handling
- ✅ Edge cases
- ✅ Coroutine behavior

## Troubleshooting

### Common Issues

1. **MockK not working**: Ensure you're using the correct import and syntax
2. **Coroutine tests failing**: Use `runTest` and `MainDispatcherRule`
3. **Robolectric issues**: Check Android SDK version compatibility
4. **Hilt testing**: Use `@HiltAndroidTest` for integration tests

### Debugging Tests

1. Add `@Test` annotations to individual test methods
2. Use `println()` or logging for debugging
3. Check test output in Android Studio's Run window
4. Use breakpoints in test methods

## Contributing

When adding new tests:
1. Follow the existing naming conventions
2. Use the provided test utilities
3. Add comprehensive test coverage
4. Include edge cases and error scenarios
5. Update this README if adding new test utilities
