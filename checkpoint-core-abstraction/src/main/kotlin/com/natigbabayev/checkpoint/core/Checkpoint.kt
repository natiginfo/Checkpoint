package com.natigbabayev.checkpoint.core

/**
 * Child of [DefaultRule] which accepts list of [DefaultRule].
 * When [Checkpoint.canPass] is invoked, function will go through all of the passed rules
 * and invoke [DefaultRule.canPass] of each rule until first item that cannot pass.
 * Create instance of class using [Checkpoint.Builder] or [checkpoint] dsl.
 */
class Checkpoint<INPUT> private constructor(
    private val rules: List<DefaultRule<INPUT>>
) : DefaultRule<INPUT>() {

    override val callback: Callback<INPUT>? = null

    override fun isValid(input: INPUT): Boolean {
        return rules.firstOrNull { !it.canPass(input) } == null
    }

    @CheckpointDslMarker
    class Builder<INPUT> {
        private val rules = mutableListOf<DefaultRule<INPUT>>()

        fun addRules(rules: List<DefaultRule<INPUT>>) = this.apply { this.rules += rules }

        fun addRule(rule: DefaultRule<INPUT>) = this.apply { this.rules += rule }

        fun build(): Checkpoint<INPUT> {
            require(rules.isNotEmpty()) { "You should set at least 1 rule." }
            return Checkpoint(rules = rules)
        }
    }
}
