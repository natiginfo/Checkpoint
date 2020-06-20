package com.natigbabayev.checkpoint.core

import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class DefaultRuleDslKtTest {

    // region Mocks
    private val mockWhenInvalid: (CharSequence) -> Unit = mockk()
    private val mockIsValid: (CharSequence) -> Boolean = mockk()
    // endregion

    @Test
    fun givenIsValid_whenNewRuleCalled_thenPassesParametersToBuilder() {
        // Arrange
        mockkConstructor(DefaultRuleBuilder::class)
        // Act
        newRule<CharSequence> {
            isValid(mockIsValid)
        }
        // Assert
        verify { anyConstructed<DefaultRuleBuilder<CharSequence>>().isValid(mockIsValid) }
    }

    @Test
    fun givenParametersAndCallback_whenNewRuleCalled_thenPassesParametersToBuilder() {
        // Arrange
        mockkConstructor(DefaultRuleBuilder::class)
        // Act
        newRule<CharSequence> {
            isValid(mockIsValid)
            whenInvalid(mockWhenInvalid)
        }
        // Assert
        verify { anyConstructed<DefaultRuleBuilder<CharSequence>>().isValid(mockIsValid) }
        verify { anyConstructed<DefaultRuleBuilder<CharSequence>>().whenInvalid(any()) }
    }
}
