package com.natigbabayev.checkpoint.core

abstract class Rule<INPUT, OUTPUT>(private val sideEffect: ((INPUT) -> Unit)?) {

    protected abstract fun isValid(input: INPUT): OUTPUT

    protected fun invokeSideEffect(input: INPUT, isValid: Boolean) {
        if (!isValid) sideEffect?.invoke(input)
    }

    abstract fun canPass(input: INPUT): OUTPUT
}
