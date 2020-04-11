package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.DefaultRule

/**
 * Rule for validating if given input's length is in given range.
 */
class LengthRangeRule private constructor(
    override val callback: Callback<CharSequence>?,
    private val minLength: Int,
    private val maxLength: Int
) : DefaultRule<CharSequence>() {

    override fun isValid(input: CharSequence): Boolean = input.length in minLength..maxLength

    class Builder {
        private var callback: Callback<CharSequence>? = null

        private var minLength: Int = 0
        private var maxLength: Int = Int.MAX_VALUE

        fun whenInvalid(callback: Callback<CharSequence>?) = apply { this.callback = callback }

        /**
         * @param length provides minimum length. If not provided, minLength will be set to 0.
         */
        fun minLength(length: Int) = apply { this.minLength = length }

        /**
         * @param length provides max length. If not provided, minLength will be set to [Int.MAX_VALUE]
         */
        fun maxLength(length: Int) = apply { this.maxLength = length }

        fun build() = LengthRangeRule(minLength = minLength, maxLength = maxLength, callback = callback)
    }
}
