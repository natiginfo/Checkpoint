package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.DefaultRule

class MaxLengthRule private constructor(
    override val callback: Callback<String>?,
    private val maxLength: Int
) : DefaultRule<String>() {

    override fun isValid(input: String): Boolean {
        return input.length <= maxLength
    }

    class Builder {
        private var callback: Callback<String>? = null

        private var maxLength: Int = 0

        fun callback(callback: Callback<String>?) = apply { this.callback = callback }

        fun maxLength(minLength: Int) = apply { this.maxLength = minLength }

        fun build() = MaxLengthRule(maxLength = maxLength, callback = callback)
    }
}
