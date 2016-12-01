package com.benjaminearley.githubapi.modelViewOnly

import android.app.Activity
import com.benjaminearley.githubapi.modelViewOnly.ViewModel
import io.reactivex.disposables.Disposable

data class Model(
        var viewModel: ViewModel,
        var networkModel: Disposable?,
        var activity: Activity?)
