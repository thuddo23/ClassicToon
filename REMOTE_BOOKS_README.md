# Remote Book Fetching Implementation

This document describes the implementation of remote book fetching functionality in the ClassicToon Android app.

## Overview

The app now supports fetching books from remote APIs and downloading their HTML content for offline reading. The implementation follows the existing clean architecture pattern and reuses all current data models, DTOs, repository, logic, and screens.

## Features

1. **Fetch Books from Remote API**: Retrieves a list of books from a remote endpoint
2. **Download Book Content**: Downloads HTML content for individual books
3. **Local Storage**: Saves downloaded books to local storage for offline reading
4. **Fallback Support**: Falls back to mock data if the remote API is unavailable
5. **Progress Indication**: Shows download progress and success/error states

## API Endpoints

The implementation expects two API endpoints:

### 1. Get All Books
```
GET /books
Query Parameters:
- page: int (default: 1)
- limit: int (default: 20)
- search: string? (optional)
- category: string? (optional)
```

**Response Format:**
```json
[
  {
    "book_id": "string",
    "title": "string",
    "author": "string",
    "people_read": "int",
    "cover": "string? (URL)",
    "category": "string"
  }
]
```

### 2. Get Book Content
```
GET /books/{bookId}/content
```

**Response:** HTML content as plain text

## Configuration

### 1. Update API Base URL

Edit `app/src/main/java/com/classictoon/novel/core/network/ApiConfig.kt`:

```kotlin
object ApiConfig {
    // Replace with your actual API base URL
    const val BASE_URL = "https://your-api-base-url.com/"
    // ... other config
}
```

### 2. Network Timeouts

You can adjust network timeouts in the same file:

```kotlin
const val CONNECT_TIMEOUT_SECONDS = 30L
const val READ_TIMEOUT_SECONDS = 60L
const val WRITE_TIMEOUT_SECONDS = 30L
```

## Implementation Details

### Data Flow

1. **UI Layer**: User clicks download button in `ServerBookDetailScreen`
2. **ViewModel**: `ServerBookDetailViewModel` calls `DownloadBookUseCase`
3. **Use Case**: `DownloadBookUseCase` orchestrates the download process
4. **Repository**: `ServerBookRepositoryImpl` fetches data from remote API
5. **API Service**: `ApiService` makes HTTP requests using Retrofit
6. **Local Storage**: Book content is saved as HTML files
7. **Database**: Book metadata is saved to local Room database

### Key Components

#### 1. ApiService
- Interface defining the remote API endpoints
- Uses Retrofit for HTTP requests
- Supports pagination, search, and category filtering

#### 2. BookListResponse
- DTO for API responses
- Maps to the existing `ServerBook` domain model

#### 3. DownloadBookUseCase
- Orchestrates the download process
- Saves HTML content to local files
- Creates local book entities
- Handles errors gracefully

#### 4. NetworkModule
- Provides Retrofit and OkHttpClient instances
- Configures logging, timeouts, and interceptors
- Uses Dagger Hilt for dependency injection

### Error Handling

- **Network Errors**: Falls back to mock data service
- **Download Failures**: Shows error messages to user
- **File System Errors**: Handles permission and storage issues
- **Graceful Degradation**: App continues to work even if remote API is unavailable

## Usage

### 1. Download a Book

1. Navigate to a book detail screen
2. Click the "Download" button
3. Wait for download to complete
4. Book is automatically saved to local library

### 2. Read Downloaded Book

1. Downloaded books appear in the local library
2. Open the book normally through the existing reader
3. Book content is loaded from local HTML files

## Dependencies Added

The following dependencies were added to support remote book fetching:

```kotlin
// Networking
implementation(libs.retrofit)
implementation(libs.retrofit.converter.gson)
implementation(libs.okhttp)
implementation(libs.okhttp.logging.interceptor)
```

## Testing

### Mock API Service

The existing `MockApiService` is kept as a fallback and for testing purposes. It provides:

- Mock book data for development
- Simulated network delays
- Sample HTML content
- Search and filtering functionality

### Testing Remote API

To test with your actual API:

1. Update `ApiConfig.BASE_URL` with your API endpoint
2. Ensure your API returns data in the expected format
3. Test network connectivity and error handling
4. Verify download functionality works correctly

## Security Considerations

- **HTTPS**: Ensure your API uses HTTPS for secure communication
- **API Keys**: If authentication is required, implement proper API key management
- **Input Validation**: Validate all API responses before processing
- **File Security**: Downloaded files are stored in app's private directory

## Future Enhancements

1. **Authentication**: Add support for user authentication
2. **Background Downloads**: Implement background download service
3. **Download Queue**: Add download queue management
4. **Progress Tracking**: Show download progress for large files
5. **Resume Downloads**: Support resuming interrupted downloads
6. **Book Updates**: Check for updated versions of downloaded books

## Troubleshooting

### Common Issues

1. **Network Timeout**: Check internet connection and API availability
2. **Download Failures**: Verify file permissions and storage space
3. **API Errors**: Check API endpoint configuration and response format
4. **Build Errors**: Ensure all dependencies are properly added

### Debug Information

Enable logging in `NetworkModule` to see detailed network requests:

```kotlin
val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
```

## Support

For issues or questions about the remote book functionality, please refer to the existing codebase structure and follow the established patterns for consistency.
