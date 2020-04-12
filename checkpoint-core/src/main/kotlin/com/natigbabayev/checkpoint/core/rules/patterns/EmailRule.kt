package com.natigbabayev.checkpoint.core.rules.patterns

import com.natigbabayev.checkpoint.core.DefaultRule
import java.util.regex.Pattern.compile

/**
 * Rule for validating if given input is valid email.
 */
class EmailRule(
    override val callback: Callback<CharSequence>?
) : DefaultRule<CharSequence>() {

    private val emailPattern = compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    override fun isValid(input: CharSequence): Boolean {
        return emailPattern.matcher(input).matches()
    }

    class Builder {
        private var callback: Callback<CharSequence>? = null

        fun whenInvalid(callback: Callback<CharSequence>?) = apply { this.callback = callback }

        fun build() = EmailRule(callback)
    }
}
