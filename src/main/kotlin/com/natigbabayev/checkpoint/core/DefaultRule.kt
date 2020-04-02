package com.natigbabayev.checkpoint.core

/**
 * Child of [Rule] with [Boolean] output
 */
abstract class DefaultRule<INPUT> : Rule<INPUT, Boolean>() {

    /**
     * This function invokes [Rule.isValid] with given input
     * and passes result to [Rule.invokeCallback].
     */
    override fun canPass(input: INPUT): Boolean {
        val isValid = isValid(input)
        invokeCallback(input, isValid)
        return isValid
    }
}
