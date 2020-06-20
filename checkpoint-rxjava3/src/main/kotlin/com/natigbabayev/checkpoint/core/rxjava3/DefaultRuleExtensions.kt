@file:JvmName("DefaultRuleExtensions")

package com.natigbabayev.checkpoint.core.rxjava3

import com.natigbabayev.checkpoint.core.DefaultRule
import io.reactivex.Single

/**
 * Convert's [DefaultRule.canPass] to [Single]
 * @return cold [Single]
 */
fun <T> DefaultRule<T>.canPassSingle(input: T) = Single.create<Boolean> {
    if (!it.isDisposed) it.onSuccess(canPass(input))
}
