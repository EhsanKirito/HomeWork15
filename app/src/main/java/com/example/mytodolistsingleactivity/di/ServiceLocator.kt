package com.example.mytodolistsingleactivity.di

import com.example.mytodolistsingleactivity.DataService
import com.example.mytodolistsingleactivity.DatabaseImp
import com.example.mytodolistsingleactivity.data.Repository

object ServiceLocator {
    fun provideDataService(): DataService {
        return DatabaseImp()
    }

    fun provideRepository(): Repository {
        return Repository(provideDataService())
    }
}