package com.benjaminearley.githubapi

import android.app.Activity
import io.reactivex.disposables.Disposable

data class Model(
        var viewModel: ViewModel,
        var networkModel: Disposable?,
        var activity: Activity?)
