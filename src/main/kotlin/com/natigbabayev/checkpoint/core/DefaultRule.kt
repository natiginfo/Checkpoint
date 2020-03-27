package com.natigbabayev.checkpoint.core

abstract class DefaultRule<INPUT>(callback: Callback<INPUT>?) :
    Rule<INPUT, Boolean>(callback) {

    override fun canPass(input: INPUT): Boolean {
        val isValid = isValid(input)
        invokeCallback(input, isValid)
        return isValid
    }
}
