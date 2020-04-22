package com.natigbabayev.checkpoint.core.rules

import com.natigbabayev.checkpoint.core.DefaultRule

/**
 * Rule for validating if any of given rules can pass.
 */
class EitherRule<INPUT> private constructor(
    private val rules: List<DefaultRule<INPUT>>,
    override val callback: Callback<INPUT>?
) : DefaultRule<INPUT>() {

    override fun isValid(input: INPUT): Boolean {
        return rules.any { it.canPass(input) }
    }

    class Builder<INPUT> {
        private var callback: Callback<INPUT>? = null
        private val rules = mutableListOf<DefaultRule<INPUT>>()

        fun addRules(rules: List<DefaultRule<INPUT>>) = apply {
            this.rules += rules
        }

        fun addRule(rule: DefaultRule<INPUT>) = apply { rules += rule }

        fun whenInvalid(callback: Callback<INPUT>) = apply {
            this.callback = callback
        }

        fun build() = EitherRule(rules = rules, callback = callback)
    }
}
