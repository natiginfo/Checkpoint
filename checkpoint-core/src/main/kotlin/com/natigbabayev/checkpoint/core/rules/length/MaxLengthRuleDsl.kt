@file:JvmName("MaxLengthRuleDsl")

package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.Rule

/**
 * @param block a lambda which will be invoked when [MaxLengthRule.canPass] returns false.
 */
inline fun MaxLengthRule.Builder.whenInvalid(crossinline block: (CharSequence) -> Unit) = apply {
    whenInvalid(Rule.Callback { block(it) })
}

/**
 * Executes the given builder block.
 *
 * @param init builder block.
 * @return returns instance of [ExactLengthRule]
 */
inline fun maxLengthRule(init: MaxLengthRule.Builder.() -> Unit): MaxLengthRule {
    val builder = MaxLengthRule.Builder()
    builder.init()
    return builder.build()
}
