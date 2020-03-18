package com.natigbabayev.checkpoint

abstract class DefaultRule<INPUT>(sideEffect: ((INPUT) -> Unit)?) :
    Rule<INPUT, Boolean>(sideEffect) {

    override fun canPass(input: INPUT): Boolean {
        val isValid = isValid(input)
        invokeSideEffect(input, isValid)
        return isValid
    }
}
