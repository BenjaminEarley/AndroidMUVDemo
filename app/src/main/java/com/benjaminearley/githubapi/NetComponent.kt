package com.benjaminearley.githubapi

import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NetModule::class))
interface NetComponent {
    fun retrofit(): Retrofit
}