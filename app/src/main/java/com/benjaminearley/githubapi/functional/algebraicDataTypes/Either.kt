package com.benjaminearley.githubapi.functional.algebraicDataTypes

private val none = SumB<Any, Unit>(Unit)

fun <A : Any> Some(value: A): Sum<A, Unit> = SumA(value)

@Suppress("UNCHECKED_CAST")
fun <A : Any> None(): Sum<A, Unit> = none as Sum<A, Unit>

fun <A : Any> Sum<A, Unit>.toNullable(): A? = with({ it }, { null })