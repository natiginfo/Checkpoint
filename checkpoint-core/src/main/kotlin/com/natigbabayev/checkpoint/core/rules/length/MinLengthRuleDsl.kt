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
 * @param whenInvalid a lambda which will be invoked when [MinLengthRule.canPass] returns false.
 * @return instance of [MinLengthRule]
 */
inline fun minLengthRule(
    minLength: Int,
    crossinline whenInvalid: (CharSequence) -> Unit
): MinLengthRule {
    val builder = MinLengthRule.Builder()
        .minLength(minLength)
        .whenInvalid(Rule.Callback { whenInvalid(it) })
    return builder.build()
}
