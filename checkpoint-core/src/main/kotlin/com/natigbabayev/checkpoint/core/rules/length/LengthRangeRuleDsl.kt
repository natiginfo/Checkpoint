@file:JvmName("LengthRangeRuleDsl")

package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.Rule

/**
 * @param block a lambda which will be invoked when [LengthRangeRule.canPass] returns false.
 */
inline fun LengthRangeRule.Builder.whenInvalid(crossinline block: (input: CharSequence) -> Unit) = apply {
    whenInvalid(Rule.Callback { block(it) })
}

/**
 * @param whenInvalid a lambda which will be invoked when [LengthRangeRule.canPass] returns false.
 * @return instance of [LengthRangeRule]
 */
inline fun lengthRangeRule(
    minLength: Int = 0,
    maxLength: Int = Int.MAX_VALUE,
    crossinline whenInvalid: (input: CharSequence) -> Unit
): LengthRangeRule {
    val builder = LengthRangeRule.Builder()
        .minLength(minLength)
        .maxLength(maxLength)
        .whenInvalid(Rule.Callback { whenInvalid(it) })
    return builder.build()
}

/**
 * @return instance of [LengthRangeRule] without any callback.
 */
fun lengthRangeRule(minLength: Int = 0, maxLength: Int = Int.MAX_VALUE): LengthRangeRule {
    val builder = LengthRangeRule.Builder()
        .minLength(minLength)
        .maxLength(maxLength)

    return builder.build()
}
