package com.benjaminearley.githubapi

// TODO: add typealias when kotlin 1.1 is available

private val none = Sum.kindB<Any, Unit>(Unit)

fun <A : Any> Some(value: A): Sum<A, Unit> = Sum.kindA<A, Unit>(value)

@Suppress("UNCHECKED_CAST")
fun <A : Any> None(): Sum<A, Unit> = none as Sum<A, Unit>

fun <A : Any> Sum<A, Unit>.toNullable(): A? = with({ it }, { null })