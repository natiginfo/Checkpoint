@file:JvmName("AnyRuleDsl")

package com.natigbabayev.checkpoint.core.rules

import com.natigbabayev.checkpoint.core.DefaultRule
import com.natigbabayev.checkpoint.core.Rule

/**
 * @param block a lambda which will be invoked when [AnyRule.canPass] returns false.
 */
inline fun <T> AnyRule.Builder<T>.whenInvalid(
    crossinline block: (input: T) -> Unit
) = apply {
    whenInvalid(Rule.Callback { block(it) })
}

/**
 * @param whenInvalid a lambda which will be invoked, when [AnyRule.canPass] returns false.
 * @return instance of [AnyRule]
 */
inline fun <T> anyRule(
    rules: List<DefaultRule<T>>,
    crossinline whenInvalid: (input: T) -> Unit
): AnyRule<T> {
    val builder = AnyRule.Builder<T>()
        .whenInvalid(Rule.Callback { whenInvalid(it) })
        .addRules(rules)

    return builder.build()
}

/**
 * @return new instance of [AnyRule] without any callback.
 */
fun <T> anyRule(rules: List<DefaultRule<T>>): AnyRule<T> {
    val builder = AnyRule.Builder<T>()
        .addRules(rules)

    return builder.build()
}
