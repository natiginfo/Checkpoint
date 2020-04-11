package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.DefaultRule

class MinLengthRule private constructor(
    override val callback: Callback<CharSequence>?,
    private val minLength: Int
) : DefaultRule<CharSequence>() {

    override fun isValid(input: CharSequence): Boolean = input.length >= minLength

    class Builder {
        private var callback: Callback<CharSequence>? = null

        private var minLength: Int = 0

        fun whenInvalid(callback: Callback<CharSequence>?) = apply { this.callback = callback }

        fun minLength(minLength: Int) = apply { this.minLength = minLength }

        fun build() = MinLengthRule(minLength = minLength, callback = callback)
    }
}
