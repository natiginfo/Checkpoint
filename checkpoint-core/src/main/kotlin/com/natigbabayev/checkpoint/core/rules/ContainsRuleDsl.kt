@file:JvmName("ContainsRuleDsl")

package com.natigbabayev.checkpoint.core.rules

import com.natigbabayev.checkpoint.core.Rule

/**
 * @param block a lambda which will be invoked when [ContainsRule.canPass] returns false.
 */
inline fun ContainsRule.Builder.whenInvalid(
    crossinline block: (input: CharSequence) -> Unit
) = apply {
    whenInvalid(Rule.Callback { block(it) })
}

/**
 * @param whenInvalid a lambda which will be invoked, when [ContainsRule.canPass] returns false.
 * @return instance of [ContainsRule]
 */
inline fun containsRule(
    other: String,
    ignoreCase: Boolean = false,
    crossinline whenInvalid: (input: CharSequence) -> Unit
): ContainsRule {
    val builder = ContainsRule.Builder()
        .ignoreCase(ignoreCase)
        .other(other)
        .whenInvalid(Rule.Callback { whenInvalid(it) })

    return builder.build()
}

/**
 * @return new instance of [ContainsRule] without any callback.
 */
fun containsRule(
    other: String,
    ignoreCase: Boolean = false
): ContainsRule {
    val builder = ContainsRule.Builder()
        .ignoreCase(ignoreCase)
        .other(other)

    return builder.build()
}

