package com.example.bitcoin.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.PrivateKey
import java.security.SecureRandom

class PrivateKeyTest {
    private lateinit var secureRandom: SecureRandom

    @BeforeEach
    fun setUp() {
        secureRandom = SecureRandom()
    }

    @Test
    fun `Functional_SuccessfulPrivateKeyGeneration_ValidHexadecimalString`() {
        val privateKey = PrivateKey.generate()
        assertEquals(64, privateKey.value.length)
        assertTrue(privateKey.value.matches(Regex("^[0-9a-f]{64}$")))
    }

    @Test
    fun `Functional_EdgeCase_MinimumValue_AllZeros_ValidPrivateKey`() {
        val bytes = ByteArray(32) { 0.toByte() }
        val privateKey = PrivateKey(bytes.joinToString(separator = "") { "%02x".format(it) })
        assertEquals("00".repeat(32), privateKey.value)
    }

    @Test
    fun `Functional_EdgeCase_MaximumValue_AllMax_ValidPrivateKey`() {
        val bytes = ByteArray(32) { 255.toByte() }
        val privateKey = PrivateKey(bytes.joinToString(separator = "") { "%02x".format(it) })
        assertEquals("ff".repeat(32), privateKey.value)
    }

    @Test
    fun `Invariant_VerifyPrivateKeyValidity_64CharacterHexadecimal`() {
        repeat(100) { // Generate multiple keys to verify each
            val privateKey = PrivateKey.generate()
            assertEquals(64, privateKey.value.length)
            assertTrue(privateKey.value.matches(Regex("^[0-9a-f]{64}$")))
        }
    }

    @Test
    fun `Invariant_VerifyPrivateKeyUniqueness_NoDuplicatesAcrossGenerations`() {
        val generatedKeys = mutableSetOf<String>()
        repeat(100) { // Generate multiple keys to check for duplicates
            val privateKey = PrivateKey.generate()
            assertTrue(generatedKeys.add(privateKey.value))
        }
    }
}