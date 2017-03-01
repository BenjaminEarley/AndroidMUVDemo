package com.benjaminearley.githubapi.functional

import com.benjaminearley.githubapi.functional.algebraicDataTypes.Sum
import com.benjaminearley.githubapi.functional.algebraicDataTypes.SumA
import com.benjaminearley.githubapi.functional.algebraicDataTypes.SumB

fun <T, F> Iterable<T>.groupStart(getField: (T) -> F): GroupChain<T, F> {
    return GroupChain(SumA(groupBy { getField(it) }))
}

class GroupChain<T, F>(val either: Sum<Map<F, List<T>>, Pair<(T) -> F, GroupChain<T, F>>>) {
    fun groupNext(getField: (T) -> F): GroupChain<T, F> = GroupChain(SumB(Pair(getField, this)))
    fun groupLast(getField: (T) -> F): Any = groupNext(getField).groupCompute { it }

    private fun groupCompute(transform: (Map<F, List<T>>) -> Any): Any = either.with(transform, {
        val getField = it.first
        it.second.groupCompute { it.mapValues { transform(it.value.groupBy(getField)) } }
    })
}
