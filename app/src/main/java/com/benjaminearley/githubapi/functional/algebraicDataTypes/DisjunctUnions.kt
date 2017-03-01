package com.benjaminearley.githubapi.functional.algebraicDataTypes

sealed class Sum3<out A : Any, out B : Any, out C : Any> {
    abstract fun <D> with(a: (A) -> D, b: (B) -> D, c: (C) -> D): D
}

class Sum3A<out A : Any, out B : Any, out C : Any>(val value: A) : Sum3<A, B, C>() {
    override fun <D> with(a: (A) -> D, b: (B) -> D, c: (C) -> D): D = a(value)
}

class Sum3B<out A : Any, out B : Any, out C : Any>(val value: B) : Sum3<A, B, C>() {
    override fun <D> with(a: (A) -> D, b: (B) -> D, c: (C) -> D): D = b(value)
}

class Sum3C<out A : Any, out B : Any, out C : Any>(val value: C) : Sum3<A, B, C>() {
    override fun <D> with(a: (A) -> D, b: (B) -> D, c: (C) -> D): D = c(value)
}

sealed class Sum4<out A : Any, out B : Any, out C : Any, out D : Any> {
    abstract fun <E> with(a: (A) -> E, b: (B) -> E, c: (C) -> E, d: (D) -> E): E
}

class Sum4A<out A : Any, out B : Any, out C : Any, out D : Any>(val value: A) : Sum4<A, B, C, D>() {
    override fun <E> with(a: (A) -> E, b: (B) -> E, c: (C) -> E, d: (D) -> E): E = a(value)
}

class Sum4B<out A : Any, out B : Any, out C : Any, out D : Any>(val value: B) : Sum4<A, B, C, D>() {
    override fun <E> with(a: (A) -> E, b: (B) -> E, c: (C) -> E, d: (D) -> E): E = b(value)
}

class Sum4C<out A : Any, out B : Any, out C : Any, out D : Any>(val value: C) : Sum4<A, B, C, D>() {
    override fun <E> with(a: (A) -> E, b: (B) -> E, c: (C) -> E, d: (D) -> E): E = c(value)
}

class Sum4D<out A : Any, out B : Any, out C : Any, out D : Any>(val value: D) : Sum4<A, B, C, D>() {
    override fun <E> with(a: (A) -> E, b: (B) -> E, c: (C) -> E, d: (D) -> E): E = d(value)
}

sealed class Sum5<out A : Any, out B : Any, out C : Any, out D : Any, out E : Any> {
    abstract fun <F> with(a: (A) -> F, b: (B) -> F, c: (C) -> F, d: (D) -> F, e: (E) -> F): F
}

class Sum5A<out A : Any, out B : Any, out C : Any, out D : Any, out E : Any>(private val value: A) : Sum5<A, B, C, D, E>() {
    override fun <F> with(a: (A) -> F, b: (B) -> F, c: (C) -> F, d: (D) -> F, e: (E) -> F): F = a(value)
}

class Sum5B<out A : Any, out B : Any, out C : Any, out D : Any, out E : Any>(private val value: B) : Sum5<A, B, C, D, E>() {
    override fun <F> with(a: (A) -> F, b: (B) -> F, c: (C) -> F, d: (D) -> F, e: (E) -> F): F = b(value)
}

class Sum5C<out A : Any, out B : Any, out C : Any, out D : Any, out E : Any>(private val value: C) : Sum5<A, B, C, D, E>() {
    override fun <F> with(a: (A) -> F, b: (B) -> F, c: (C) -> F, d: (D) -> F, e: (E) -> F): F = c(value)
}

class Sum5D<out A : Any, out B : Any, out C : Any, out D : Any, out E : Any>(private val value: D) : Sum5<A, B, C, D, E>() {
    override fun <F> with(a: (A) -> F, b: (B) -> F, c: (C) -> F, d: (D) -> F, e: (E) -> F): F = d(value)
}

class Sum5E<out A : Any, out B : Any, out C : Any, out D : Any, out E : Any>(private val value: E) : Sum5<A, B, C, D, E>() {
    override fun <F> with(a: (A) -> F, b: (B) -> F, c: (C) -> F, d: (D) -> F, e: (E) -> F): F = e(value)
}
