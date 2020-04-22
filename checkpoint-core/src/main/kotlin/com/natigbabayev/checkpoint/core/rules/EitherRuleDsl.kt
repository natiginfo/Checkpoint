@file:JvmName("EitherRuleDsl")

package com.natigbabayev.checkpoint.core.rules

import com.natigbabayev.checkpoint.core.DefaultRule
import com.natigbabayev.checkpoint.core.Rule

/**
 * @param block a lambda which will be invoked when [EitherRule.canPass] returns false.
 */
inline fun <T> EitherRule.Builder<T>.whenInvalid(
    crossinline block: (input: T) -> Unit
) = apply {
    whenInvalid(Rule.Callback { block(it) })
}

/**
 * @param whenInvalid a lambda which will be invoked, when [EitherRule.canPass] returns false.
 * @return instance of [EitherRule]
 */
inline fun <T> eitherRule(
    rules: List<DefaultRule<T>>,
    crossinline whenInvalid: (input: T) -> Unit
): EitherRule<T> {
    val builder = EitherRule.Builder<T>()
        .whenInvalid(Rule.Callback { whenInvalid(it) })
        .addRules(rules)

    return builder.build()
}

/**
 * @return new instance of [EitherRule] without any callback.
 */
fun <T> eitherRule(rules: List<DefaultRule<T>>): EitherRule<T> {
    val builder = EitherRule.Builder<T>()
        .addRules(rules)

    return builder.build()
}
