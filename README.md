# Buku Manager

Buku Manager is an Android application for managing a collection of books. Users can add, view, and mark books as favorites. The app supports offline access using a local database and synchronizes data with a remote server.

## Features

- List all books with details and cover images
- Add new books with image upload
- Mark/unmark books as favorites
- View favorite books
- Offline support using Room database
- Synchronization with remote server via REST API

## Project Structure

- `app/` - Android application source code
  - `src/main/java/com/example/x9090/buku/` - Main Java source files
  - `src/main/res/` - Resources (layouts, drawables, values)
  - `build.gradle` - Module build configuration
- `build.gradle` - Project build configuration
- `gradle/` - Gradle wrapper files

## Requirements

- Android Studio 3.1+
- Android SDK 28+
- Java 8

## Setup

1. Clone the repository:
    ```sh
    git clone <repo-url>
    ```
2. Open the project in Android Studio.
3. Build and run the app on an emulator or device.

## Dependencies

- [Android Room](https://developer.android.com/topic/libraries/architecture/room)
- [Volley](https://developer.android.com/training/volley)
- [Glide](https://github.com/bumptech/glide)
- [UploadService](https://github.com/gotev/android-upload-service)

## API

The app communicates with a REST API hosted at:

- `https://bukumanager.000webhostapp.com/v1/Api.php`

Endpoints are defined in [`Api`](app/src/main/java/com/example/x9090/buku/Api.java).

---
