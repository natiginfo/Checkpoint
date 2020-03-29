@file:JvmName("CheckpointDsl")

package com.natigbabayev.checkpoint.core

@DslMarker
annotation class CheckpointDslMarker

/**
 * Executes the given builder block.
 *
 * @param init builder block.
 * @return returns instance of [Checkpoint]
 */
inline fun <T> checkpoint(init: Checkpoint.Builder<T>.() -> Unit): Checkpoint<T> {
    val builder = Checkpoint.Builder<T>()
    builder.init()
    return builder.build()
}
