package com.natigbabayev.checkpoint.core.rxjava2

import com.natigbabayev.checkpoint.core.DefaultRule
import io.reactivex.Single

/**
 * Child of [SingleRule] which accepts list of [SingleRule].
 * When [SingleCheckpoint.canPass] is invoked, function will go through all of the passed rules
 * and invoke [SingleCheckpoint.canPass] of each rule until first item that cannot pass.
 * Create instance of class using [SingleCheckpoint.Builder].
 */
class SingleCheckpoint<INPUT> private constructor(
    private val singleRules: List<SingleRule<INPUT>>,
    private val defaultRules: List<DefaultRule<INPUT>>
) : SingleRule<INPUT>() {

    override val callback: Callback<INPUT>? = null

    override fun isValid(input: INPUT): Single<Boolean> {
        val canPassFromSingleRules = singleRules.map { it.canPass(input) }
        val canPassFromDefaultRules = defaultRules.map { it.canPassSingle(input) }
        val canPassResult = canPassFromDefaultRules + canPassFromSingleRules

        return Single.merge(canPassResult)
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
        private val singleRules = mutableListOf<SingleRule<INPUT>>()
        private val defaultRules = mutableListOf<DefaultRule<INPUT>>()

        @JvmName("addDefaultRules")
        fun addRules(rules: List<DefaultRule<INPUT>>) = apply {
            defaultRules += rules
        }

        fun addRule(rule: DefaultRule<INPUT>) = apply {
            defaultRules += rule
        }

        fun addRules(rules: List<SingleRule<INPUT>>) = apply {
            singleRules += rules
        }

        fun addRule(rule: SingleRule<INPUT>) = apply {
            singleRules += rule
        }

        fun build(): SingleCheckpoint<INPUT> {
            require((singleRules + defaultRules).isNotEmpty()) {
                "You should set at least 1 rule."
            }

            return SingleCheckpoint(
                singleRules = singleRules,
                defaultRules = defaultRules
            )
        }
    }
}
