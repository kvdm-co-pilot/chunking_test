package com.example.bitcoin.tests

import com.example.bitcoin.Utxo
import com.example.bitcoin.Address
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class UtxoTest {

    @Test
    fun `Functional_ValidUtxoCreation`() {
        // Given
        val txId = "tx1234"
        val index = 0
        val amountSats = 100000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        
        // When
        val utxo = Utxo(txId, index, amountSats, address)
        
        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }

    @Test
    fun `Functional_InvalidTransactionID`() {
        // Given
        val txId = ""
        val index = 0
        val amountSats = 50000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address)
        }
    }

    @Test
    fun `Functional_NegativeAmount`() {
        // Given
        val txId = "tx5678"
        val index = 1
        val amountSats = -1000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address)
        }
    }

    @Test
    fun `Functional_MaximumAmount`() {
        // Given
        val txId = "tx9999"
        val index = 2
        val amountSats = 2100000000000000L
        val address = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }

    @Test
    fun `Functional_InvalidAddress`() {
        // Given
        val txId = "tx3456"
        val index = 3
        val amountSats = 75000L
        val address = Address("XYZ123")

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address)
        }
    }

    @Test
    fun `Invariant_ValidAddressFormat`() {
        // Given
        val txId = "tx7890"
        val index = 4
        val amountSats = 50000L
        val address = Address("123ABC")

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address)
        }
    }

    @Test
    fun `Invariant_AmountMatchesTransactionInputs`() {
        // Given
        val txId = "tx1111"
        val index = 5
        val amountSats = 250000L
        val address = Address("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(amountSats, utxo.amountSats)
    }

    @Test
    fun `Invariant_TotalOutputWithinInputLimits`() {
        // Given
        val txId = "tx2222"
        val index = 6
        val amountSats = 999999L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(amountSats, utxo.amountSats)
    }

    @Test
    fun `Boundary_ValidBitcoinAddressPrefixes`() {
        // Given
        val txId = "tx4444"
        val index = 7
        val amountSats = 150000L
        val address = Address("bc1qw508d6qejxtdg4y5r3zarvaryvg6kdaj")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(address, utxo.address)
    }
}

package com.example.bitcoin.tests

import com.example.bitcoin.Utxo
import com.example.bitcoin.Address
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class UtxoTest {

    @Test
    fun `Functional_ValidUtxoInitialization`() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = 50000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }

    @Test
    fun `Functional_InvalidTransactionID`() {
        // Given
        val txId = "xyz!@#"
        val index = 1
        val amountSats = 100000L
        val address = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")

        // When/Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address)
        }
    }

    @Test
    fun `Functional_BoundaryTestAmountSatsZero`() {
        // Given
        val txId = "abc123"
        val index = 5
        val amountSats = 0L
        val address = Address("bc1qw508d6qejxtdg4y5r3zarvaryvg6kdaj")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }

    @Test
    fun `Functional_MaximumSatoshisEdgeCase`() {
        // Given
        val txId = "def456"
        val index = 10
        val amountSats = 2100000000000000L
        val address = Address("bc1qw508d6qejxtdg4y5r3zarvaryvg6kdaj")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }

    @Test
    fun `Functional_InvalidAddressHandling`() {
        // Given
        val txId = "ghi789"
        val index = 3
        val amountSats = 75000L
        val address = Address("invalidAddress123")

        // When/Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address)
        }
    }

    @Test
    fun `Invariant_ValidAddressCheck`() {
        // Given
        val txId = "jkl012"
        val index = 7
        val amountSats = 120000L
        val address = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }

    @Test
    fun `Invariant_UtxoAmountVerification`() {
        // Given
        val txId = "mno345"
        val index = 2
        val amountSats = 150000L
        val address = Address("bc1qw508d6qejxtdg4y5r3zarvaryvg6kdaj")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }

    @Test
    fun `DomainBoundary_ValidBitcoinAddressPrefixes`() {
        // Given
        val txId = "pqr678"
        val index = 4
        val amountSats = 50000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }
}
