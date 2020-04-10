package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.DefaultRule

class LengthRangeRule private constructor(
    override val callback: Callback<String>?,
    private val minLength: Int,
    private val maxLength: Int
) : DefaultRule<String>() {

    override fun isValid(input: String): Boolean = minLength >= input.length && input.length <= maxLength

    class Builder {
        private var callback: Callback<String>? = null

        private var minLength: Int = 0
        private var maxLength: Int = 0

        fun callback(callback: Callback<String>?) = apply { this.callback = callback }

        fun minLength(length: Int) = apply { this.minLength = length }
        fun maxLength(length: Int) = apply { this.maxLength = length }

        fun build() = LengthRangeRule(minLength = minLength, maxLength = maxLength, callback = callback)
    }
}
