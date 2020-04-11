package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.DefaultRule

/**
 * Rule for validating if given input's length is less than or equal to required length.
 */
class MaxLengthRule private constructor(
    override val callback: Callback<CharSequence>?,
    private val maxLength: Int
) : DefaultRule<CharSequence>() {

    override fun isValid(input: CharSequence): Boolean {
        return input.length <= maxLength
    }

    class Builder {
        private var callback: Callback<CharSequence>? = null

        private var maxLength: Int = Int.MAX_VALUE

        fun whenInvalid(callback: Callback<CharSequence>?) = apply { this.callback = callback }

        /**
         * @param maxLength if not provided, will be set to [Int.MAX_VALUE].
         */
        fun maxLength(maxLength: Int) = apply { this.maxLength = maxLength }

        fun build() = MaxLengthRule(maxLength = maxLength, callback = callback)
    }
}
