package com.natigbabayev.checkpoint.core.rules

import com.natigbabayev.checkpoint.core.DefaultRule

/**
 * Rule for validating if given input contains [ContainsRule.other].
 */
class ContainsRule private constructor(
    private val other: CharSequence,
    private val ignoreCase: Boolean,
    override val callback: Callback<CharSequence>?
) : DefaultRule<CharSequence>() {

    override fun isValid(input: CharSequence): Boolean {
        return input.contains(other = other, ignoreCase = ignoreCase)
    }

    class Builder {
        private var callback: Callback<CharSequence>? = null
        private var other: CharSequence? = null
        private var ignoreCase: Boolean = false

        fun whenInvalid(callback: Callback<CharSequence>) = apply {
            this.callback = callback
        }

        fun other(other: CharSequence) = apply {
            this.other = other
        }

        fun ignoreCase(ignoreCase: Boolean) = apply {
            this.ignoreCase = ignoreCase
        }

        fun build(): ContainsRule {
            require(other != null) {
                "You have to set other()"
            }
            return ContainsRule(
                other = other!!,
                ignoreCase = ignoreCase,
                callback = callback
            )
        }

    }
}
