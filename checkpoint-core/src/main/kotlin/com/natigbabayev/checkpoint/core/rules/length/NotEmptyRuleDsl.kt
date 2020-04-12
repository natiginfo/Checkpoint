@file:JvmName("NotEmptyRuleDsl")

package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.Rule

/**
 * @param block a lambda which will be invoked when [NotEmptyRule.canPass] returns false.
 */
inline fun NotEmptyRule.Builder.whenInvalid(crossinline block: (input: CharSequence) -> Unit) = apply {
    whenInvalid(Rule.Callback { block(it) })
}

/**
 * @param whenInvalid a lambda which will be invoked when [NotEmptyRule.canPass] returns false.
 * @return instance of [NotEmptyRule]
 */
inline fun notEmptyRule(
    crossinline whenInvalid: (input: CharSequence) -> Unit
): NotEmptyRule {
    val builder = NotEmptyRule.Builder()
        .whenInvalid(Rule.Callback { whenInvalid(it) })

    return builder.build()
}

/**
 * @return instance of [NotEmptyRule] without any callback
 */
fun notEmptyRule(): NotEmptyRule {
    val builder = NotEmptyRule.Builder()
    return builder.build()
}
