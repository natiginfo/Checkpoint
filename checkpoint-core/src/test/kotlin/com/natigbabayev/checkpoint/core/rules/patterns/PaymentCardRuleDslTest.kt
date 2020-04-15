package com.natigbabayev.checkpoint.core.rules.patterns

import com.natigbabayev.checkpoint.core.Rule
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class PaymentCardRuleDslTest {

    // region Mocks
    private val mockWhenInvalid: (CharSequence) -> Unit = mockk()
    // endregion

    @Test
    fun givenAcceptedCards_whenPaymentCardRuleCalled_thenPassesAcceptedCardsToBuilder() {
        // Arrange
        mockkConstructor(PaymentCardRule.Builder::class)
        val cardTypes = listOf(PaymentCardRule.CardType.MAESTRO, PaymentCardRule.CardType.UNION_PAY)
        // Act
        paymentCardRule(acceptedCardTypes = cardTypes)
        // Assert
        verify { anyConstructed<PaymentCardRule.Builder>().acceptedCardTypes(cardTypes) }
    }

    @Test
    fun givenCallback_whenPaymentCardRuleCalled_thenPassesCallbackToBuilder() {
        // Arrange
        mockkConstructor(PaymentCardRule.Builder::class)
        val capturedCallback = mutableListOf<Rule.Callback<CharSequence>>()
        // Act
        paymentCardRule(whenInvalid = mockWhenInvalid)
        // Assert
        verify { anyConstructed<PaymentCardRule.Builder>().whenInvalid(capture(capturedCallback)) }
    }

    @Test
    fun givenCallbackAndAcceptedCards_whenPaymentCardRuleCalled_thenPassesCallbackAndAcceptedCardsToBuilder() {
        // Arrange
        mockkConstructor(PaymentCardRule.Builder::class)
        val cardTypes = listOf(PaymentCardRule.CardType.MAESTRO, PaymentCardRule.CardType.UNION_PAY)
        val capturedCallback = mutableListOf<Rule.Callback<CharSequence>>()
        // Act
        paymentCardRule(acceptedCardTypes = cardTypes, whenInvalid = mockWhenInvalid)
        // Assert
        verify { anyConstructed<PaymentCardRule.Builder>().whenInvalid(capture(capturedCallback)) }
        verify { anyConstructed<PaymentCardRule.Builder>().acceptedCardTypes(cardTypes) }
    }
}
