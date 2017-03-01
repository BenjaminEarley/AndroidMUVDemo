package com.benjaminearley.githubapi.functional

fun <T, U, P> ((T, U) -> P).partial(t: T): (U) -> P =
        { u -> this(t, u) }

fun <T, U, P, L> ((T, U, P) -> L).partial(t: T, u: U): (P) -> L =
        { p -> this(t, u, p) }

fun <T, U, P, L, E> ((T, U, P, L) -> E).partial(t: T, u: U, p: P): (L) -> E =
        { l -> this(t, u, p, l) }

fun <T, U, P, L, E, S> ((T, U, P, L, E) -> S).partial(t: T, u: U, p: P, l: L): (E) -> S =
        { e -> this(t, u, p, l, e) }

val <T, U, P> ((T, U) -> P).curry: (T) -> (U) -> P
    get() = { t -> { u -> this(t, u) } }

val <T, U, P, L> ((T, U, P) -> L).curry: (T) -> (U) -> (P) -> L
    get() = { t -> { u -> { p -> this(t, u, p) } } }

val <T, U, P, L, E> ((T, U, P, L) -> E).curry: (T) -> (U) -> (P) -> (L) -> E
    get() = { t -> { u -> { p -> { l -> this(t, u, p, l) } } } }

val <T, U, P, L, E, S> ((T, U, P, L, E) -> S).curry: (T) -> (U) -> (P) -> (L) -> (E) -> S
    get() = { t -> { u -> { p -> { l -> { e -> this(t, u, p, l, e) } } } } }

infix inline fun <T, U> T.pipe(t: (T) -> U): U = t(this)
