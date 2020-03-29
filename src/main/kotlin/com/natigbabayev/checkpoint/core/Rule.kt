package com.natigbabayev.checkpoint.core

/**
 * Rules are main part of checkpoint. [Rule.canPass] function can be invoked directly or used by child classes.
 * When new rule is needed, this class can be extended with the desired input and output type.
 *
 * @param INPUT value which will be validated
 * @param OUTPUT result of [Rule.isValid].
 *               This can be [Boolean] or any other desired return value (i.e. Observable<Boolean>).
 */
abstract class Rule<INPUT, OUTPUT> {

    /**
     * @param [INPUT] should be same as input of [Rule]
     */
    interface Callback<INPUT> {
        /**
         * This function must be invoked when [Rule.isValid] returns false.
         */
        fun whenInvalid(input: INPUT)

        /**
         * Constructs a callback for a lambda. This compact syntax is most useful for inline callbacks.
         */
        companion object {
            inline operator fun <T> invoke(crossinline block: (input: T) -> Unit): Callback<T> {
                return object : Callback<T> {
                    override fun whenInvalid(input: T) = block(input)
                }
            }
        }
    }

    /**
     * Callback which is used by [Rule.invokeCallback] when [Rule.isValid] returns false
     */
    protected abstract val callback: Callback<INPUT>?

    /**
     * Function used to define whether a condition is satisfied with given input.
     * Typically, this function should be invoked from [Rule.canPass].
     */
    protected abstract fun isValid(input: INPUT): OUTPUT

    /**
     * When [isValid] is false, this function will invoke [Callback.whenInvalid].
     * Typically, this function should be invoked from [Rule.canPass], after result of [Rule.isValid] is ready.
     *
     * @param input input which used for validation
     * @param isValid result of [Rule.isValid]
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
