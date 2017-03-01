package com.benjaminearley.githubapi

import android.app.Application
import com.benjaminearley.githubapi.dagger.*


class MyApp : Application() {

    companion object {
        lateinit var netComponent: NetComponent
        lateinit var gitHubComponent: GitHubComponent
    }

    override fun onCreate() {
        super.onCreate()

        netComponent = DaggerNetComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule("https://api.github.com"))
                .build()

        gitHubComponent = DaggerGitHubComponent.builder()
                .netComponent(netComponent)
                .gitHubModule(GitHubModule())
                .build()

    }
}
