package com.benjaminearley.githubapi.functional.algebraicDataTypes

sealed class Maybe<out T : Any> {
    abstract fun <U : Any> flatMap(map: (T) -> Maybe<U>): Maybe<U>

    object nothing : Maybe<Nothing>() {
        override fun <U : Any> flatMap(map: (Nothing) -> Maybe<U>): Maybe<U> = this
    }

    class something<out T : Any>(val value: T) : Maybe<T>() {
        override fun <U : Any> flatMap(map: (T) -> Maybe<U>): Maybe<U> = map(value)
    }
}
