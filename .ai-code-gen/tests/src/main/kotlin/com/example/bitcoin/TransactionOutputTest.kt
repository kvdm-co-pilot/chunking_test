```kotlin
package com.example.bitcoin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class TransactionOutputTest {

    @Test
    fun `ValidTransactionOutputCreation_ValidP2PKHAddress`() {
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val amountSats = 100000L
        val scriptPubKey = "OP_DUP OP_HASH160 <PubKeyHash> OP_EQUALVERIFY OP_CHECKSIG"

        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `ValidTransactionOutputCreation_ValidP2SHAddress`() {
        val address = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")
        val amountSats = 500000L
        val scriptPubKey = "OP_HASH160 <ScriptHash> OP_EQUAL"

        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `ValidTransactionOutputCreation_ValidBech32Address`() {
        val address = Address("bc1qw508d6qejxtdg4y5r3zarvary0c5xw7kygt080")
        val amountSats = 200000L
        val scriptPubKey = "0 <WitnessProgram>"

        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `ValidTransactionOutputCreation_MinimumNonDustAmount`() {
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val amountSats = 546L
        val scriptPubKey = "OP_DUP OP_HASH160 <PubKeyHash> OP_EQUALVERIFY OP_CHECKSIG"

        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `ValidTransactionOutputCreation_MaximumAmount`() {
        val address = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")
        val amountSats = 2100000000000000L
        val scriptPubKey = "OP_HASH160 <ScriptHash> OP_EQUAL"

        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `ZeroAmountTransactionOutput_ValidP2PKHAddress`() {
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val amountSats = 0L
        val scriptPubKey = "OP_DUP OP_HASH160 <PubKeyHash> OP_EQUALVERIFY OP_CHECKSIG"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `ZeroAmountTransactionOutput_ValidP2SHAddress`() {
        val address = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")
        val amountSats = 0L
        val scriptPubKey = "OP_HASH160 <ScriptHash> OP_EQUAL"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `ZeroAmountTransactionOutput_ValidBech32Address`() {
        val address = Address("bc1qw508d6qejxtdg4y5r3zarvary0c5xw7kygt080")
        val amountSats = 0L
        val scriptPubKey = "0 <WitnessProgram>"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `NegativeAmountTransactionOutput_ValidP2PKHAddress`() {
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val amountSats = -1000L
        val scriptPubKey = "OP_DUP OP_HASH160 <PubKeyHash> OP_EQUALVERIFY OP_CHECKSIG"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `NegativeAmountTransactionOutput_ValidP2SHAddress`() {
        val address = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")
        val amountSats = -1000L
        val scriptPubKey = "OP_HASH160 <ScriptHash> OP_EQUAL"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `NegativeAmountTransactionOutput_ValidBech32Address`() {
        val address = Address("bc1qw508d6qejxtdg4y5r3zarvary0c5xw7kygt080")
        val amountSats = -1000L
        val scriptPubKey = "0 <WitnessProgram>"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `InvalidAddressTransactionOutput_MalformedP2PKHAddress`() {
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7")
        val amountSats = 1000L
        val scriptPubKey = "OP_DUP OP_HASH160 <PubKeyHash> OP_EQUALVERIFY OP_CHECKSIG"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `InvalidAddressTransactionOutput_MalformedP2SHAddress`() {
        val address = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWN")
        val amountSats = 1000L
        val scriptPubKey = "OP_HASH160 <ScriptHash> OP_EQUAL"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `InvalidAddressTransactionOutput_MalformedBech32Address`() {
        val address = Address("bc1qw508d6qejxtdg4y5r3zarvary0c5xw7kygt08")
        val amountSats = 1000L
        val scriptPubKey = "0 <WitnessProgram>"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `MaximumSatoshiLimitBoundary_ValidP2PKHAddress`() {
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val amountSats = 2100000000000000L
        val scriptPubKey = "OP_DUP OP_HASH160 <PubKeyHash> OP_EQUALVERIFY OP_CHECKSIG"

        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `MaximumSatoshiLimitBoundary_ValidP2SHAddress`() {
        val address = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")
        val amountSats = 2100000000000000L
        val scriptPubKey = "OP_HASH160 <ScriptHash> OP_EQUAL"

        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `MaximumSatoshiLimitBoundary_ValidBech32Address`() {
        val address = Address("bc1qw508d6qejxtdg4y5r3zarvary0c5xw7kygt080")
        val amountSats = 2100000000000000L
        val scriptPubKey = "0 <WitnessProgram>"

        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `InvalidScriptPubKeyFormatTransactionOutput_ValidP2PKHAddress`() {
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val amountSats = 100000L
        val scriptPubKey = "OP_HASH160 <InvalidScript> OP_EQUAL"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `InvalidScriptPubKeyFormatTransactionOutput_ValidP2SHAddress`() {
        val address = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")
        val amountSats = 100000L
        val scriptPubKey = "OP_DUP OP_HASH160 <InvalidScript> OP_EQUALVERIFY OP_CHECKSIG"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `InvalidScriptPubKeyFormatTransactionOutput_ValidBech32Address`() {
        val address = Address("bc1qw508d6qejxtdg4y5r3zarvary0c5xw7kygt080")
        val amountSats = 100000L
        val scriptPubKey = "<InvalidFormat>"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `ErrorCondition_OverflowAmountBeyondMaxSatoshiLimit`() {
        val address = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")
        val amountSats = 2100000000000001L
        val scriptPubKey = "OP_HASH160 <ScriptHash> OP_EQUAL"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `ErrorCondition_ScriptPubKeyMismatchWithAddressType`() {
        val address = Address("bc1qw508d6qejxtdg4y5r3zarvary0c5xw7kygt080")
        val amountSats = 100000L
        val scriptPubKey = "OP_DUP OP_HASH160 <PubKeyHash> OP_EQUALVERIFY OP_CHECKSIG"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }
}
```