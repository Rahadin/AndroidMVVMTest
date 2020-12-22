package com.example.myapplication

import com.example.myapplication.di.ApiModule
import com.example.myapplication.model.AnimalApiService

class ApiModuleTest(val mockService: AnimalApiService): ApiModule() {

    override fun provideAnimalApiService(): AnimalApiService {
        return mockService
    }
}