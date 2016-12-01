package com.benjaminearley.githubapi.modelViewOnly

import android.view.View
import com.benjaminearley.githubapi.User


sealed class UserResult {
    class None() : UserResult() {
        override fun <D> with(b: (Unit) -> D, a: (User) -> D, c: (Throwable) -> D): D = b(Unit)
    }

    class Some(val user: User) : UserResult() {
        override fun <D> with(b: (Unit) -> D, a: (User) -> D, c: (Throwable) -> D): D = a(user)
    }

    class Error(val error: Throwable) : UserResult() {
        override fun <D> with(b: (Unit) -> D, a: (User) -> D, c: (Throwable) -> D): D = c(error)
    }

    abstract fun <D> with(none: (Unit) -> D, some: (User) -> D, error: (Throwable) -> D): D
}

class ViewModel(var view: View)
