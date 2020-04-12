@file:JvmName("NotBlankRuleDsl")

package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.Rule

/**
 * @param block a lambda which will be invoked when [NotBlankRule.canPass] returns false.
 */
inline fun NotBlankRule.Builder.whenInvalid(crossinline block: (input: CharSequence) -> Unit) = apply {
    whenInvalid(Rule.Callback { block(it) })
}

/**
 * @param whenInvalid a lambda which will be invoked when [NotBlankRule.canPass] returns false.
 * @return instance of [NotBlankRule]
 */
inline fun notBlankRule(
    crossinline whenInvalid: (input: CharSequence) -> Unit
): NotBlankRule {
    val builder = NotBlankRule.Builder()
        .whenInvalid(Rule.Callback { whenInvalid(it) })

    return builder.build()
}

/**
 * @return instance of [NotBlankRule] without any callback.
 */
fun notBlankRule(): NotBlankRule {
    val builder = NotBlankRule.Builder()
    return builder.build()
}
