package com.natigbabayev.checkpoint.core.rules.patterns

import com.natigbabayev.checkpoint.core.DefaultRule

/**
 * Rule for validating if given input matches the pattern.
 */
class RegexRule private constructor(
    private val pattern: String,
    override val callback: Callback<CharSequence>?
) : DefaultRule<CharSequence>() {
    private val regex = Regex(pattern)

    override fun isValid(input: CharSequence): Boolean {
        return input.matches(regex)
    }

    class Builder {
        private var callback: Callback<CharSequence>? = null
        private var pattern: String? = null

        fun whenInvalid(callback: Callback<CharSequence>) = apply {
            this.callback = callback
        }

        fun pattern(pattern: String) = apply {
            this.pattern = pattern
        }

        fun build(): RegexRule {
            require(pattern != null) { "You must set pattern" }

            return RegexRule(
                callback = callback,
                pattern = pattern!!
            )
        }
    }
}
