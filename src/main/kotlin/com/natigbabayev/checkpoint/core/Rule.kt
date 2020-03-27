package com.natigbabayev.checkpoint.core

abstract class Rule<INPUT, OUTPUT> @JvmOverloads constructor(private val callback: Callback<INPUT>?) {

    /**
     * @param [INPUT] should be same as input of [Rule]
     */
    interface Callback<INPUT> {
        /**
         * This function must be invoked when [Rule.isValid] returns false.
         */
        fun whenInvalid(input: INPUT)
    }

    /**
     * Function used to define whether a condition is satisfied with given input.
     * Typically, this function should be invoked from [Rule.canPass].
     */
    protected abstract fun isValid(input: INPUT): OUTPUT

    /**
     * When [isValid] is false, this function will invoke [Callback.whenInvalid].
     * Typically, this function should be invoked from [Rule.canPass], after result of [Rule.isValid] is ready.
     *
     * @param input - input which used for validation
     * @param isValid - result of [Rule.isValid]
     */
    protected fun invokeCallback(input: INPUT, isValid: Boolean) {
        if (!isValid) callback?.whenInvalid(input)
    }

    /**
     * Main entry point of class. This function should invoke [Rule.isValid] with given input
     * and pass result to [Rule.invokeCallback].
     */
    abstract fun canPass(input: INPUT): OUTPUT
}
