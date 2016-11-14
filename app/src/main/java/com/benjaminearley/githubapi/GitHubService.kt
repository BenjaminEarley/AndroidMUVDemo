package com.benjaminearley.githubapi

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("/users/{login}")
    fun getUser(
            @Path("login") login: String): Observable<User?>
}