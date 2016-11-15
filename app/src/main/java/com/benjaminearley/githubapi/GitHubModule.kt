package com.benjaminearley.githubapi

import dagger.Module
import dagger.Provides
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path


@Module
class GitHubModule {

    interface GitHubApiInterface {
        @GET("/users/{login}")
        fun getUser(
                @Path("login") login: String): Single<User>
    }

    @Provides
    @ApiScope
    fun providesGitHubInterface(retrofit: Retrofit): GitHubApiInterface {
        return retrofit.create<GitHubApiInterface>(GitHubApiInterface::class.java)
    }
}