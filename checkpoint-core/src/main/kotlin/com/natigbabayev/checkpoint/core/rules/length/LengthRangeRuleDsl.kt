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
 * Executes the given builder block.
 *
 * @param init builder block.
 * @return returns instance of [LengthRangeRule]
 */
inline fun lengthRangeRule(init: LengthRangeRule.Builder.() -> Unit): LengthRangeRule {
    val builder = LengthRangeRule.Builder()
    builder.init()
    return builder.build()
}
