# Trending-Cermati

## Getting Start

You can open this Project with Android Studio *3.4.1*.

## Prerequisites

What things you need to install the software and install them :
If you using Mac OS, you could install this with [Homebrew](homebrew.sh)

- Java 8
- Gradle

## Installing

A step by step to get a development env running

```
./gradlew assembleDevDebug
```

To install in emulator or device
```
./gradlew installDevDebug
```

To check dependencies using

```
./gradlew app:dependencies
```

## Features

- API Call with [Retrofit](https://square.github.io/retrofit)
- Dependency injection (with [Koin](https://insert-koin.io))
- Reactive programming with RxJava 2 and RxAndroid
- Google Design library
- Android architecture components with MVVM
    
## Supported devices

The template support every device with a SDK level of at least 16 (Android 4+)


| **Splash Screen** | **Main Screen** | **Result Search Screen** |
| ------ | ------ | ------ |
| ![alt text](https://i.postimg.cc/nVGqC19B/Screenshot-2020-04-13-10-07-11-04.png "Splash Screen") | ![alt text](https://i.postimg.cc/zvzbn9HW/Screenshot-2020-04-13-10-07-15-61.png "Main Screen") | ![alt text](https://i.postimg.cc/fLKkTBqb/Screenshot-2020-04-13-10-07-29-43.png "Result Search Screen")

