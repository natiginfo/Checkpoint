@file:JvmName("CheckpointDsl")

package com.natigbabayev.checkpoint.core

@DslMarker
annotation class CheckpointDslMarker

/**
 * Executes the given builder block.
 *
 * @param init builder block.
 * @return instance of [Checkpoint]
 */
inline fun <T> checkpoint(init: Checkpoint.Builder<T>.() -> Unit): Checkpoint<T> {
    val builder = Checkpoint.Builder<T>()
    builder.init()
    return builder.build()
}

/**
 * Executes the given builder block and adds created rule to [Checkpoint.Builder].
 *
 * @param init builder block.
 */
inline fun <T> Checkpoint.Builder<T>.addRule(init: DefaultRuleBuilder<T>.() -> Unit) {
    val defaultRuleBuilder = DefaultRuleBuilder<T>()
    defaultRuleBuilder.init()
    addRule(defaultRuleBuilder.build())
}

/**
 * @param block a lambda which will be invoked when [DefaultRule.canPass] returns false.
 */
inline fun <T> DefaultRuleBuilder<T>.whenInvalid(crossinline block: (input: T) -> Unit) = apply {
    whenInvalid(Rule.Callback { block(it) })
}