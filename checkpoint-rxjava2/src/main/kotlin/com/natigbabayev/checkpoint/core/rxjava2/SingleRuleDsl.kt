package com.natigbabayev.checkpoint.core.rxjava2

/**
 * Executes the given builder block and returns new instance of [SingleRule].
 *
 * @param init builder block.
 */
inline fun <T> newSingleRule(init: SingleRuleBuilder<T>.() -> Unit): SingleRule<T> {
    val defaultRuleBuilder = SingleRuleBuilder<T>()
    defaultRuleBuilder.init()
    return defaultRuleBuilder.build()
}
