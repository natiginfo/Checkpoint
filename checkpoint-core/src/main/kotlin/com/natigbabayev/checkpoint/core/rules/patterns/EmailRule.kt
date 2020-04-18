package com.natigbabayev.checkpoint.core.rules.patterns

import com.natigbabayev.checkpoint.core.DefaultRule

/**
 * Rule for validating if given input is valid email.
 */
class EmailRule private constructor(
    override val callback: Callback<CharSequence>?
) : DefaultRule<CharSequence>() {

    private val emailPattern = Regex(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    override fun isValid(input: CharSequence): Boolean {
        return input.matches(emailPattern)
    }

    class Builder {
        private var callback: Callback<CharSequence>? = null

        fun whenInvalid(callback: Callback<CharSequence>?) = apply { this.callback = callback }

        fun build() = EmailRule(callback)
    }
}
