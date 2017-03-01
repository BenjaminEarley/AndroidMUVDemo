package com.benjaminearley.githubapi.modelUpdateView

import com.benjaminearley.githubapi.data.User
import com.benjaminearley.githubapi.functional.algebraicDataTypes.Sum3
import com.benjaminearley.githubapi.functional.algebraicDataTypes.Sum3A
import com.benjaminearley.githubapi.functional.algebraicDataTypes.Sum3B
import com.benjaminearley.githubapi.functional.algebraicDataTypes.Sum3C

typealias UserResult = Sum3<Unit, User, Throwable>
fun UserResultOfNone(): UserResult = Sum3A(Unit)
fun UserResultOfSome(user: User): UserResult = Sum3B(user)
fun UserResultOfError(error: Throwable): UserResult = Sum3C(error)

class ViewModel(var userResult: UserResult, var isProgressBarVisible: Boolean, var userName: String?, var cursorPosition: Int)
