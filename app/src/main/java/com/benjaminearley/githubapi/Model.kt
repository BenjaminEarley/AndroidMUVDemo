package com.benjaminearley.githubapi

import io.reactivex.disposables.Disposable

data class Model(
        var viewModel: ViewModel,
        var networkModel: Disposable?)
