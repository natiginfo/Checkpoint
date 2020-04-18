@file:JvmName("RegexRuleDsl")

package com.natigbabayev.checkpoint.core.rules.patterns

import com.natigbabayev.checkpoint.core.Rule

/**
 * @param block a lambda which will be invoked when [RegexRule.canPass] returns false.
 */
inline fun RegexRule.Builder.whenInvalid(
    crossinline block: (input: CharSequence) -> Unit
) = apply {
    whenInvalid(Rule.Callback { block(it) })
}

/**
 * @param whenInvalid a lambda which will be invoked, when [RegexRule.canPass] returns false.
 * @return instance of [RegexRule]
 */
inline fun regexRule(
    pattern: String,
    crossinline whenInvalid: (input: CharSequence) -> Unit
): RegexRule {
    val builder = RegexRule.Builder()
        .pattern(pattern)
        .whenInvalid(Rule.Callback { whenInvalid(it) })

    return builder.build()
}

/**
 * @return new instance of [RegexRule] without any callback.
 */
fun regexRule(pattern: String): RegexRule {
    val builder = RegexRule.Builder()
        .pattern(pattern)

    return builder.build()
}
