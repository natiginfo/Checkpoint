package com.natigbabayev.checkpoint.core.rxjava3

import com.natigbabayev.checkpoint.core.Rule
import io.reactivex.Single
import io.reactivex.functions.BiFunction

/**
 * Child of [Rule] with [Single<Boolean>] output.
 */
abstract class SingleRule<INPUT> : Rule<INPUT, Single<Boolean>>() {

    /**
     * This function invokes [Rule.isValid] with given input
     * and passes result to [Rule.invokeCallback].
     */
    override fun canPass(input: INPUT): Single<Boolean> {
        return Single.zip(
            Single.just(input),
            isValid(input),
            BiFunction { rawInput, isValid ->
                invokeCallback(rawInput, isValid)
                isValid
            }
        )
    }
}
