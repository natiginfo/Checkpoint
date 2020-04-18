package com.natigbabayev.checkpoint.core

/**
 * Builder for creating instance of [DefaultRule] without extending it.
 */
@CheckpointDslMarker
class DefaultRuleBuilder<INPUT> {
    private var callback: Rule.Callback<INPUT>? = null
    private var predicate: ((input: INPUT) -> Boolean)? = null

    /**
     * @param callback which will be invoked when [DefaultRule.canPass] returns false.
     */
    fun whenInvalid(callback: Rule.Callback<INPUT>) = apply {
        this.callback = callback
    }

    /**
     * Sets validation predicate which will be used when [DefaultRule.canPass] is invoked.
     */
    fun isValid(predicate: (input: INPUT) -> Boolean) = apply {
        this.predicate = predicate
    }

    fun build(): DefaultRule<INPUT> {
        require(predicate != null) { "You must set predicate using isValid() function." }
        return object : DefaultRule<INPUT>() {
            override val callback: Callback<INPUT>? = this@DefaultRuleBuilder.callback
            override fun isValid(input: INPUT): Boolean = predicate!!(input)
        }
    }
}
