package com.natigbabayev.checkpoint.core

/**
 * Executes the given builder block and returns new instance of [DefaultRule].
 *
 * @param init builder block.
 */
inline fun <T> newRule(init: DefaultRuleBuilder<T>.() -> Unit): DefaultRule<T> {
    val defaultRuleBuilder = DefaultRuleBuilder<T>()
    defaultRuleBuilder.init()
    return defaultRuleBuilder.build()
}
