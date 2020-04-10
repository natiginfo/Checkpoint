package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.DefaultRule

class MinLengthRule private constructor(
    override val callback: Callback<String>?,
    private val minLength: Int
) : DefaultRule<String>() {

    override fun isValid(input: String): Boolean = input.length >= minLength

    class Builder {
        private var callback: Callback<String>? = null

        private var minLength: Int = 0

        fun callback(callback: Callback<String>?) = apply { this.callback = callback }

        fun minLength(minLength: Int) = apply { this.minLength = minLength }

        fun build() = MinLengthRule(minLength = minLength, callback = callback)
    }
}
