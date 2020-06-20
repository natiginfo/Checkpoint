package com.natigbabayev.checkpoint.core.rxjava2

import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class SingleRuleDslKtTest {

    // region Mocks
    private val mockWhenInvalid: (CharSequence) -> Unit = mockk()
    private val mockIsValid: (CharSequence) -> Boolean = mockk()
    // endregion

    @Test
    fun givenIsValid_whenNewSingleRuleCalled_thenPassesParametersToBuilder() {
        // Arrange
        mockkConstructor(SingleRuleBuilder::class)
        // Act
        newSingleRule<CharSequence> {
            isValid(mockIsValid)
        }
        // Assert
        verify { anyConstructed<SingleRuleBuilder<CharSequence>>().isValid(mockIsValid) }
    }

    @Test
    fun givenParametersAndCallback_whenNewSingleRuleCalled_thenPassesParametersToBuilder() {
        // Arrange
        mockkConstructor(SingleRuleBuilder::class)
        // Act
        newSingleRule<CharSequence> {
            isValid(mockIsValid)
            whenInvalid(mockWhenInvalid)
        }
        // Assert
        verify { anyConstructed<SingleRuleBuilder<CharSequence>>().isValid(mockIsValid) }
        verify { anyConstructed<SingleRuleBuilder<CharSequence>>().whenInvalid(any()) }
    }
}
