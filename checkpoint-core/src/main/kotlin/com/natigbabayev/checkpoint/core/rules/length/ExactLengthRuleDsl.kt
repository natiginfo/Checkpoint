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
 * @param whenInvalid a lambda which will be invoked when [ExactLengthRule.canPass] returns false.
 * @return instance of [ExactLengthRule]
 */
inline fun exactLengthRule(
    length: Int,
    crossinline whenInvalid: (input: CharSequence) -> Unit
): ExactLengthRule {
    val builder = ExactLengthRule.Builder()
        .length(length)
        .whenInvalid(Rule.Callback { whenInvalid(it) })

    return builder.build()
}

/**
 * @return instance of [ExactLengthRule] without any callback.
 */
fun exactLengthRule(length: Int): ExactLengthRule {
    val builder = ExactLengthRule.Builder()
        .length(length)

    return builder.build()
}
