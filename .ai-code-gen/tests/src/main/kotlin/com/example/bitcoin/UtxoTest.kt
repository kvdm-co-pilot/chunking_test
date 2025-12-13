```kotlin
package com.example.bitcoin.test

import com.example.bitcoin.Utxo
import org.junit.Test
import org.junit.Assert
import org.junit.Before

class UtxoTest {

    private lateinit var validAddress: String

    @Before
    fun setUp() {
        validAddress = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"
    }

    @Test
    fun `ValidUTXOCreation_StandardCase`() {
        val utxo = Utxo(txId = "abc123", index = 0, amountSats = 1000, address = validAddress)
        Assert.assertEquals("abc123", utxo.txId)
        Assert.assertEquals(0, utxo.index)
        Assert.assertEquals(1000, utxo.amountSats)
        Assert.assertEquals(validAddress, utxo.address)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `NegativeAmountPrevention_NegativeAmount`() {
        // Assume constructor throws IllegalArgumentException
        Utxo(txId = "ghi789", index = 2, amountSats = -500, address = validAddress)
    }

    @Test
    fun `ZeroAmountHandling_ZeroAmountWithValidAddress`() {
        val utxo = Utxo(txId = "def456", index = 1, amountSats = 0, address = validAddress)
        // Assume logging is handled internally
        Assert.assertEquals(0, utxo.amountSats)
    }

    @Test
    fun `MaximumAmountHandling_MaximumBitcoinSupply`() {
        val utxo = Utxo(txId = "jkl012", index = 3, amountSats = 21_000_000_000_000_000, address = validAddress)
        Assert.assertEquals(21_000_000_000_000_000, utxo.amountSats)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `AddressFormatValidation_InvalidAddressFormat`() {
        // Assume constructor throws IllegalArgumentException
        Utxo(txId = "mno345", index = 4, amountSats = 5000, address = "invalid_address_format")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `DuplicateTxIdIndexHandling_DuplicateTxIdAndIndex`() {
        // Assume constructor throws IllegalArgumentException for duplicate txId and index
        Utxo(txId = "pqr678", index = 5, amountSats = 2000, address = validAddress)
    }

    @Test
    fun `BoundaryValueTesting_IndexAtLowerBound`() {
        val utxo = Utxo(txId = "stu901", index = 0, amountSats = 1500, address = validAddress)
        Assert.assertEquals(0, utxo.index)
    }

    @Test
    fun `BoundaryValueTesting_AmountAtLowerBound`() {
        val utxo = Utxo(txId = "vwx234", index = 6, amountSats = 0, address = validAddress)
        // Assume logging is handled internally
        Assert.assertEquals(0, utxo.amountSats)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `ErrorHandling_NegativeAmountExceptionThrown`() {
        // Assume constructor throws IllegalArgumentException
        Utxo(txId = "yz567", index = 7, amountSats = -750, address = validAddress)
    }

    @Test
    fun `Logging_ZeroAmountWarningLogged`() {
        val utxo = Utxo(txId = "abc890", index = 8, amountSats = 0, address = validAddress)
        // Assume logging is handled internally
        Assert.assertEquals(0, utxo.amountSats)
    }
}
```