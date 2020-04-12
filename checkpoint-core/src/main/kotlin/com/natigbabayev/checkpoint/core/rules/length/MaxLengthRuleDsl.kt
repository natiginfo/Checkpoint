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
 * @param whenInvalid a lambda which will be invoked when [MaxLengthRule.canPass] returns false.
 * @return instance of [MaxLengthRule]
 */
inline fun maxLengthRule(
    maxLength: Int,
    crossinline whenInvalid: (CharSequence) -> Unit
): MaxLengthRule {
    val builder = MaxLengthRule.Builder()
        .maxLength(maxLength)
        .whenInvalid(Rule.Callback { whenInvalid(it) })
    return builder.build()
}
