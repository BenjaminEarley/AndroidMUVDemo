package com.benjaminearley.githubapi.functional.algebraicDataTypes

sealed class Sum<out A : Any, out B : Any> {
    abstract fun <C> with(l: (A) ->  C, r: (B) -> C): C
}

class SumA<out A : Any, out B : Any>(val value: A) : Sum<A, B>() {
    override fun <C> with(l: (A) -> C, r: (B) -> C): C = l(value)
}

class SumB<out A : Any, out B : Any>(val value: B) : Sum<A, B>() {
    override fun <C> with(l: (A) -> C, r: (B) -> C): C = r(value)
}
