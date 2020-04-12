package com.natigbabayev.checkpoint.core.rules.patterns

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class EmailRuleDslTest {
    @Suppress("PrivatePropertyName")
    private lateinit var SUT: EmailRule

    // region Mocks
    private val mockWhenInvalid: (CharSequence) -> Unit = mockk()
    // endregion

    @BeforeEach
    fun setUp() {
        SUT = emailRule(mockWhenInvalid)
    }

    @Nested
    @DisplayName("Given invalid input")
    inner class GivenInvalidInput {
        private val invalidEmailAddresses = listOf(
            "plainaddress",
            "#@%^val%#\$@#\$@#.com",
            "@example.com",
            "Joe Smith <email@example.com>",
            "email.example.com",
            "email@example@example.com",
            "email@example.com (Joe Smith)",
            "email@example",
            "email@-example.com",
            "email@example..com",
            "“(),:;<>[\\]@example.com",
            "just\"not\"right@example.com",
            "this\\ is\"really\"not\\allowed@example.com"
        )

        @Test
        fun whenCanPassCalled_returnsFalse() {
            // Arrange
            every { mockWhenInvalid(any()) } answers { nothing }
            // Act
            val result = invalidEmailAddresses.map { SUT.canPass(it) }.toSet()
            // Assert
            assertTrue { result.size == 1 }
            assertFalse { result.first() }
        }

        @Test
        fun whenCanPassCalled_invokesCallback() {
            // Arrange
            every { mockWhenInvalid(any()) } answers { nothing }
            // Act
            invalidEmailAddresses.forEach { SUT.canPass(it) }
            // Assert
            verify {
                invalidEmailAddresses.forEach { mockWhenInvalid(it) }
            }
            confirmVerified(mockWhenInvalid)
        }
    }

    @Test
    fun givenValidInput_whenCanPassCalled_returnsTrue() {
        // Arrange
        every { mockWhenInvalid(any()) } answers { nothing }
        val validEmails = listOf(
            "email@example.com",
            "firstname.lastname@example.com",
            "email@subdomain.example.com",
            "firstname+lastname@example.com",
            "email@123.123.123.123",
            "1234567890@example.com",
            "email@example-one.com",
            "_______@example.com",
            "email@example.name",
            "email@example.museum",
            "email@example.co.jp",
            "firstname-lastname@example.co",
            "email@example.web"
        )
        // Act
        val result = validEmails.map { SUT.canPass(it) }.toSet()
        // Assert
        assertTrue { result.size == 1 }
        assertTrue { result.first() }
    }
}
