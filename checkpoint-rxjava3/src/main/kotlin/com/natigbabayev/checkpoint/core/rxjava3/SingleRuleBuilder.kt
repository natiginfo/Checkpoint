package com.natigbabayev.checkpoint.core.rxjava3

import com.natigbabayev.checkpoint.core.Rule
import io.reactivex.Single

/**
 * Builder for creating instance of [SingleRule] without extending it.
 */
@SingleCheckpointDslMarker
class SingleRuleBuilder<INPUT> {
    private var callback: Rule.Callback<INPUT>? = null
    private var predicate: ((input: INPUT) -> Boolean)? = null

    /**
     * @param callback which will be invoked when [SingleRule.canPass] returns false.
     */
    fun whenInvalid(callback: Rule.Callback<INPUT>) = apply {
        this.callback = callback
    }

    /**
     * Sets validation predicate which will be used when [SingleRule.canPass] is invoked.
     */
    fun isValid(predicate: (input: INPUT) -> Boolean) = apply {
        this.predicate = predicate
    }

    fun build(): SingleRule<INPUT> {
        require(predicate != null) { "You must set predicate using isValid() function." }
        return object : SingleRule<INPUT>() {
            override val callback: Callback<INPUT>? = this@SingleRuleBuilder.callback
            override fun isValid(input: INPUT): Single<Boolean> = Single.create {
                if (!it.isDisposed) it.onSuccess(predicate!!(input))
            }
        }
    }
}
