package com.natigbabayev.checkpoint.core

fun <T> checkpoint(init: Checkpoint.Builder<T>.() -> Unit): Checkpoint<T> {
    val builder = Checkpoint.Builder<T>()
    builder.init()
    return builder.build()
}
