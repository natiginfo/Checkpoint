package com.natigbabayev.checkpoint.core.rxjava2

import com.natigbabayev.checkpoint.core.DefaultRule
import io.reactivex.Single

/**
 * Child of [SingleRule] which accepts list of [SingleRule].
 * When [SingleCheckpoint.canPass] is invoked, function will go through all of the passed rules
 * and invoke [DefaultRule.canPass] of each rule until first item that cannot pass.
 * Create instance of class using [SingleCheckpoint.Builder].
 */
class SingleCheckpoint<INPUT> private constructor(
    private val rules: List<SingleRule<INPUT>>
) : SingleRule<INPUT>() {

    override val callback: Callback<INPUT>? = null

    override fun isValid(input: INPUT): Single<Boolean> {
        return Single.merge(rules.map { it.canPass(input) })
            .reduce { previous: Boolean, current: Boolean ->
                val isAllValid = previous && current
                check(isAllValid)
                isAllValid
            }
            .onErrorReturnItem(false)
            .toSingle(false)
    }


    @SingleCheckpointDslMarker
    class Builder<INPUT> {
        private val rules = mutableListOf<SingleRule<INPUT>>()

        fun addRules(rules: List<SingleRule<INPUT>>) = apply { this.rules += rules }

        fun addRule(rule: SingleRule<INPUT>) = apply { this.rules += rule }

        fun build(): SingleCheckpoint<INPUT> {
            require(rules.isNotEmpty()) { "You should set at least 1 rule." }
            return SingleCheckpoint(rules = rules)
        }
    }
}
