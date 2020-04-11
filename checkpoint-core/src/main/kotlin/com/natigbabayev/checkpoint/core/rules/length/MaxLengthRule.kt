package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.DefaultRule

class MaxLengthRule private constructor(
    override val callback: Callback<CharSequence>?,
    private val maxLength: Int
) : DefaultRule<CharSequence>() {

    override fun isValid(input: CharSequence): Boolean {
        return input.length <= maxLength
    }

    class Builder {
        private var callback: Callback<CharSequence>? = null

        private var maxLength: Int = 0

        fun whenInvalid(callback: Callback<CharSequence>?) = apply { this.callback = callback }

        fun maxLength(maxLength: Int) = apply { this.maxLength = maxLength }

        fun build() = MaxLengthRule(maxLength = maxLength, callback = callback)
    }
}
