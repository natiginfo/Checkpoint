package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.DefaultRule

/**
 * Rule for validating if given input's length is equal to required length.
 */
class ExactLengthRule private constructor(
    override val callback: Callback<CharSequence>?,
    private val length: Int
) : DefaultRule<CharSequence>() {

    override fun isValid(input: CharSequence): Boolean = input.length == length

    class Builder {
        private var callback: Callback<CharSequence>? = null

        private var length: Int = 0

        fun whenInvalid(callback: Callback<CharSequence>?) = apply { this.callback = callback }

        fun length(length: Int) = apply { this.length = length }

        fun build() = ExactLengthRule(length = length, callback = callback)
    }
}
