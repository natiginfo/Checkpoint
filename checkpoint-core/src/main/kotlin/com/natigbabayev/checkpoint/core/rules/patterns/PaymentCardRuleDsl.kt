@file:JvmName("PaymentCardRuleDsl")

package com.natigbabayev.checkpoint.core.rules.patterns

import com.natigbabayev.checkpoint.core.Rule

/**
 * @param block a lambda which will be invoked
 *              when [PaymentCardRule.canPass] returns false.
 */
inline fun PaymentCardRule.Builder.whenInvalid(
    crossinline block: (input: CharSequence) -> Unit
) = apply {
    whenInvalid(Rule.Callback { block(it) })
}

/**
 * @param whenInvalid a lambda which will be invoked,
 *                    when [PaymentCardRule.canPass] returns false.
 * @return instance of [PaymentCardRule]
 */
inline fun paymentCardRule(
    acceptedCardTypes: List<PaymentCardRule.CardType>? = null,
    crossinline whenInvalid: (input: CharSequence) -> Unit
): PaymentCardRule {
    val builder = PaymentCardRule.Builder()
        .whenInvalid(Rule.Callback { whenInvalid(it) })

    acceptedCardTypes?.let { builder.acceptedCardTypes(it) }

    return builder.build()
}

/**
 * @return instance of [PaymentCardRule] without any callback.
 */
fun paymentCardRule(
    acceptedCardTypes: List<PaymentCardRule.CardType>? = null
): PaymentCardRule {
    val builder = PaymentCardRule.Builder()
    acceptedCardTypes?.let { builder.acceptedCardTypes(it) }

    return builder.build()
}
