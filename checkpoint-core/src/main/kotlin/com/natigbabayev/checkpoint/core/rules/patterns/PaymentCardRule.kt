package com.natigbabayev.checkpoint.core.rules.patterns

import com.natigbabayev.checkpoint.core.DefaultRule
import com.natigbabayev.checkpoint.core.rules.patterns.PaymentCardRule.CardType

/**
 * Rule for validating if input is valid payment card number.
 * Validation is done against [CardType.cardNumberPattern].
 */
class PaymentCardRule private constructor(
    override val callback: Callback<CharSequence>?,
    private val acceptedCardTypes: List<CardType>
) : DefaultRule<CharSequence>() {

    enum class CardType(val cardNumberPattern: String) {
        AMEX("^3[47][0-9]{5,}$"),
        MASTERCARD("^(5[1-5]|2[2-7])[0-9]{5,}$"),
        VISA("^4[0-9]{6,}$"),
        MAESTRO("^(?:5[0678]\\d\\d|6304|6390|67\\d\\d)\\d{8,15}$"),
        DINERS_CLUB("^3(?:0[0-5]|[68][0-9])[0-9]{4,}$"),
        JCB("^(?:2131|1800|35[0-9]{3})[0-9]{3,}$"),
        DISCOVER("^6(?:011|5[0-9]{2})[0-9]{3,}$"),
        UNION_PAY("^62[0-5]\\d{13,16}$")
    }

    override fun isValid(input: CharSequence): Boolean {
        return acceptedCardTypes
            .map { it.cardNumberPattern.toRegex().matches(input) }
            .firstOrNull { it } ?: false
    }

    class Builder {
        private var callback: Callback<CharSequence>? = null
        private var acceptedCardTypes: Set<CardType>? = null

        /**
         * Sets card types that should be used for validation.
         * If nothing is passed, [PaymentCardRule] will accept all
         * available card types which are defined in [CardType].
         */
        fun acceptedCardTypes(cardTypes: List<CardType>) = apply {
            acceptedCardTypes = cardTypes.toSet()
        }

        fun whenInvalid(callback: Callback<CharSequence>?) = apply {
            this.callback = callback
        }

        fun build(): PaymentCardRule {
            val cardTypes = acceptedCardTypes?.toList() ?: CardType.values().toList()

            require(cardTypes.isNotEmpty()) {
                "You have to set at least one accepted card type"
            }

            return PaymentCardRule(
                callback = callback,
                acceptedCardTypes = cardTypes
            )
        }
    }
}
