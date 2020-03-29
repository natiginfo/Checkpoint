package com.natigbabayev.checkpoint.core

class Checkpoint<INPUT> private constructor(
    private val rules: List<DefaultRule<INPUT>>
) : DefaultRule<INPUT>() {

    override val callback: Callback<INPUT>? = null

    override fun isValid(input: INPUT): Boolean {
        return rules.firstOrNull { !it.canPass(input) } == null
    }

    class Builder<INPUT> {
        private val rules = mutableListOf<DefaultRule<INPUT>>()

        fun addRule(vararg rules: DefaultRule<INPUT>) = this.apply { this.rules += rules }

        fun addRule(init: DefaultRuleBuilder<INPUT>.() -> Unit) {
            val defaultRuleBuilder = DefaultRuleBuilder<INPUT>()
            defaultRuleBuilder.init()
            rules += defaultRuleBuilder.build()
        }

        fun build(): Checkpoint<INPUT> {
            check(rules.isNotEmpty()) { "You should set at least 1 rule." }
            return Checkpoint(rules = rules)
        }
    }
}
