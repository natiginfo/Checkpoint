package com.natigbabayev.checkpoint.core

abstract class DefaultRule<INPUT> : Rule<INPUT, Boolean>() {

    override fun canPass(input: INPUT): Boolean {
        val isValid = isValid(input)
        invokeCallback(input, isValid)
        return isValid
    }
}
