package com.example.myapplication.di

import com.example.myapplication.model.AnimalApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: AnimalApiService)
}