package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.DefaultRule

class ExactLengthRule private constructor(
    override val callback: Callback<String>?,
    private val length: Int
) : DefaultRule<String>() {

    override fun isValid(input: String): Boolean = input.length == length

    class Builder {
        private var callback: Callback<String>? = null

        private var length: Int = 0

        fun callback(callback: Callback<String>?) = apply { this.callback = callback }

        fun length(length: Int) = apply { this.length = length }

        fun build() = ExactLengthRule(length = length, callback = callback)
    }
}
