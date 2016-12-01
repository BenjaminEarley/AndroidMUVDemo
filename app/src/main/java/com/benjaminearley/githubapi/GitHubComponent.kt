package com.benjaminearley.githubapi

import dagger.Component

@ApiScope
@Component(dependencies = arrayOf(NetComponent::class), modules = arrayOf(GitHubModule::class))
interface GitHubComponent {
    fun inject(activity: com.benjaminearley.githubapi.modelViewOnly.MainActivity)
    fun inject(activity: com.benjaminearley.githubapi.modelUpdateView.MainActivity)
}
