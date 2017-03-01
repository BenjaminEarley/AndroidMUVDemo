package com.benjaminearley.githubapi.functional.algebraicDataTypes

data class Product<out A : Any, out B : Any>(val a: A,  val b: B) {
    infix fun <C : Any> _and(c: C): Product3<A, B, C> = Product3(a, b, c)
}

data class Product3<out A : Any, out B : Any, out C : Any>(val a: A, val b: B, val c: C) {
    infix fun <D : Any> _and(d: D): Product4<A, B, C, D> = Product4(a, b, c, d)
}

data class Product4<out A : Any, out B : Any, out C : Any, out D : Any>(val a: A, val b: B, val c: C, val d: D) {
    infix fun <E : Any> _and(e: E): Product5<A, B, C, D, E> = Product5(a, b, c, d, e)
}

data class Product5<out A : Any, out B : Any, out C : Any, out D : Any, out E : Any>(val a: A, val b: B, val c: C, val d: D, val e: E)

infix fun <A : Any, B : Any> A._and(b: B): Product<A, B> = Product(this, b)
