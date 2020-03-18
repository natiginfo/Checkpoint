package com.natigbabayev.checkpoint

class Checkpoint<INPUT>(
    private vararg val validators: DefaultRule<INPUT>
) : DefaultRule<INPUT>(null) {

    override fun isValid(input: INPUT): Boolean {
        return validators.firstOrNull { !it.canPass(input) } == null
    }
}
