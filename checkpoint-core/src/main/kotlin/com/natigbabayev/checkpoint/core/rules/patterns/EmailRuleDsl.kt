@file:JvmName("EmailRuleDsl")

package com.natigbabayev.checkpoint.core.rules.patterns

import com.natigbabayev.checkpoint.core.Rule

/**
 * @param block a lambda which will be invoked when [EmailRule.canPass] returns false.
 */
inline fun EmailRule.Builder.whenInvalid(crossinline block: (input: CharSequence) -> Unit) = apply {
    whenInvalid(Rule.Callback { block(it) })
}

/**
 * @param whenInvalid a lambda which will be invoked when [EmailRule.canPass] returns false.
 * @return instance of [EmailRule]
 */
inline fun emailRule(
    crossinline whenInvalid: (input: CharSequence) -> Unit
): EmailRule {
    val builder = EmailRule.Builder()
        .whenInvalid(Rule.Callback { whenInvalid(it) })

    return builder.build()
}

/**
 * @return instance of [EmailRule] without any callback.
 */
fun emailRule(): EmailRule {
    val builder = EmailRule.Builder()
    return builder.build()
}
