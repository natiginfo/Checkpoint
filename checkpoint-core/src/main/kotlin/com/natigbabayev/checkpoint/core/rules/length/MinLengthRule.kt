package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.DefaultRule

/**
 * Rule for validating if given input's length is more than or equal to required length.
 */
class MinLengthRule private constructor(
    override val callback: Callback<CharSequence>?,
    private val minLength: Int
) : DefaultRule<CharSequence>() {

    override fun isValid(input: CharSequence): Boolean = input.length >= minLength

    class Builder {
        private var callback: Callback<CharSequence>? = null

        private var minLength: Int = 0

        fun whenInvalid(callback: Callback<CharSequence>?) = apply { this.callback = callback }

        /**
         * @param minLength if not provided, minLength will be set to 0.
         */
        fun minLength(minLength: Int) = apply { this.minLength = minLength }

        fun build() = MinLengthRule(minLength = minLength, callback = callback)
    }
}
