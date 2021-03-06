package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.DefaultRule

/**
 * Rule for validating if given input is blank. Uses [CharSequence.isNotBlank].
 */
class NotBlankRule private constructor(
    override val callback: Callback<CharSequence>?
) : DefaultRule<CharSequence>() {

    override fun isValid(input: CharSequence): Boolean = input.isNotBlank()

    class Builder {
        private var callback: Callback<CharSequence>? = null

        fun whenInvalid(callback: Callback<CharSequence>?) = apply { this.callback = callback }

        fun build() = NotBlankRule(callback)
    }
}
