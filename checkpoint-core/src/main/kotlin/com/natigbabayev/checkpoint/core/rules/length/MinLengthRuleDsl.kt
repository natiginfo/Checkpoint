@file:JvmName("MinLengthRuleDsl")

package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.Rule

/**
 * @param block a lambda which will be invoked when [MinLengthRule.canPass] returns false.
 */
inline fun MinLengthRule.Builder.whenInvalid(crossinline block: (input: CharSequence) -> Unit) = apply {
    whenInvalid(Rule.Callback { block(it) })
}

/**
 * Executes the given builder block.
 *
 * @param init builder block.
 * @return returns instance of [MinLengthRule]
 */
inline fun minLengthRule(init: MinLengthRule.Builder.() -> Unit): MinLengthRule {
    val builder = MinLengthRule.Builder()
    builder.init()
    return builder.build()
}
