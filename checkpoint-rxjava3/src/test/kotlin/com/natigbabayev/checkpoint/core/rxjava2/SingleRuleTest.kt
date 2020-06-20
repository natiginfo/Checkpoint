package com.natigbabayev.checkpoint.core.rxjava2

import com.natigbabayev.checkpoint.core.Rule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TestableSingleRule(override val callback: Callback<CharSequence>?) : SingleRule<CharSequence>() {

    @Suppress("PropertyName")
    var _isValid: Boolean = false

    override fun isValid(input: CharSequence): Single<Boolean> {
        return Single.just(_isValid)
    }
}

internal class DefaultRuleTest {

    @Suppress("PrivatePropertyName")
    private lateinit var SUT: TestableSingleRule

    // region Mocks
    private val mockCallback = mockk<Rule.Callback<CharSequence>>()
    // endregion

    @BeforeEach
    fun setup() {
        SUT = TestableSingleRule(mockCallback)
        every { mockCallback.whenInvalid(any()) } answers { nothing }
    }

    @Nested
    @DisplayName("Given invalid input")
    inner class GivenInvalidInput {
        @Test
        fun whenCanPassCalled_thenCallbackIsInvoked() {
            // Arrange
            SUT._isValid = false
            // Act
            SUT.canPass("input").test()
            // Assert
            verify(exactly = 1) { mockCallback.whenInvalid("input") }
        }

        @Test
        fun whenCanPassCalled_thenReturnsFalse() {
            // Arrange
            SUT._isValid = false
            // Act
            val result = SUT.canPass("input").test()
            // Assert
            result.assertValue(false)
        }
    }

    @Nested
    @DisplayName("Given valid input")
    inner class GivenValidInput {
        @Test
        fun whenCanPassCalled_thenCallbackIsNeverInvoked() {
            // Arrange
            SUT._isValid = true
            // Act
            SUT.canPass("input").test()
            // Assert
            verify(exactly = 0) { mockCallback.whenInvalid("input") }
        }

        @Test
        fun whenCanPassCalled_thenReturnsTrue() {
            // Arrange
            SUT._isValid = true
            // Act
            val result = SUT.canPass("input").test()
            // Assert
            result.assertValue(true)
        }
    }
}
