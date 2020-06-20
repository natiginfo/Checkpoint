@file:JvmName("SingleCheckpointDsl")

package com.natigbabayev.checkpoint.core.rxjava3

import com.natigbabayev.checkpoint.core.Rule

/**
 * Executes the given builder block.
 *
 * @param init builder block.
 * @return instance of [SingleCheckpoint]
 */
inline fun <T> singleCheckpoint(init: SingleCheckpoint.Builder<T>.() -> Unit): SingleCheckpoint<T> {
    val builder = SingleCheckpoint.Builder<T>()
    builder.init()
    return builder.build()
}

/**
 * Executes the given builder block and adds created rule to [SingleCheckpoint.Builder].
 *
 * @param init builder block.
 */
inline fun <T> SingleCheckpoint.Builder<T>.addRule(
    init: SingleRuleBuilder<T>.() -> Unit
) {
    val defaultRuleBuilder = SingleRuleBuilder<T>()
    defaultRuleBuilder.init()
    addRule(defaultRuleBuilder.build())
}

/**
 * @param block a lambda which will be invoked when [SingleRule.canPass] returns false.
 */
inline fun <T> SingleRuleBuilder<T>.whenInvalid(crossinline block: (input: T) -> Unit) = apply {
    whenInvalid(Rule.Callback { block(it) })
}
