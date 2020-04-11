@file:JvmName("ExactLengthRuleDsl")

package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.Rule

/**
 * @param block a lambda which will be invoked when [ExactLengthRule.canPass] returns false.
 */
inline fun ExactLengthRule.Builder.whenInvalid(crossinline block: (input: CharSequence) -> Unit) = apply {
    whenInvalid(Rule.Callback { block(it) })
}

/**
 * Executes the given builder block.
 *
 * @param init builder block.
 * @return returns instance of [ExactLengthRule]
 */
inline fun exactLengthRule(init: ExactLengthRule.Builder.() -> Unit): ExactLengthRule {
    val builder = ExactLengthRule.Builder()
    builder.init()
    return builder.build()
}