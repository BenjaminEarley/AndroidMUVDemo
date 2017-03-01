package com.benjaminearley.githubapi.dagger

import dagger.Component

@ApiScope
@Component(dependencies = arrayOf(NetComponent::class), modules = arrayOf(GitHubModule::class))
interface GitHubComponent {
    fun inject(activity: com.benjaminearley.githubapi.modelViewOnly.ViewOnlyActiity)
    fun inject(activity: com.benjaminearley.githubapi.modelUpdateView.MUVActivity)
}
