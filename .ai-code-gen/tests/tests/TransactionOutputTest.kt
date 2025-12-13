com.example.bitcoin

org.junit.jupiter.api.Assertions
org.junit.jupiter.api.Test

class TransactionOutputTest {

    @Test
    @Test
    fun testCreateTransactionOutputWithValidAddress() {
        // Given a valid Bitcoin address is provided
        val validAddress = Address("validBitcoinAddress")
        
        // When a TransactionOutput is created
        val transactionOutput = TransactionOutput(address = validAddress, amountSats = 1000L, scriptPubKey = "validScriptPubKey")
        
        // Then the TransactionOutput should be successfully created
        Assertions.assertNotNull(transactionOutput)
        Assertions.assertEquals(validAddress, transactionOutput.address)
    }

    @Test
    @Test
    fun testCreateTransactionOutputWithNullAddress() {
        // Given a null address is provided
        val nullAddress: Address? = null
        
        // When a TransactionOutput is created
        // Then an error should be thrown indicating invalid address
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address = nullAddress!!, amountSats = 1000L, scriptPubKey = "validScriptPubKey")
        }
    }

    @Test
    @Test
    fun testCreateTransactionOutputWithValidAmountSats() {
        // Given a positive amountSats is provided
        val validAmountSats = 1000L
        
        // When a TransactionOutput is created
        val transactionOutput = TransactionOutput(address = Address("validBitcoinAddress"), amountSats = validAmountSats, scriptPubKey = "validScriptPubKey")
        
        // Then the TransactionOutput should be successfully created
        Assertions.assertNotNull(transactionOutput)
        Assertions.assertEquals(validAmountSats, transactionOutput.amountSats)
    }

    @Test
    @Test
    fun testCreateTransactionOutputWithNegativeAmountSats() {
        // Given a negative amountSats is provided
        val negativeAmountSats = -1000L
        
        // When a TransactionOutput is created
        // Then an error should be thrown indicating invalid amount
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address = Address("validBitcoinAddress"), amountSats = negativeAmountSats, scriptPubKey = "validScriptPubKey")
        }
    }

    @Test
    @Test
    fun testCreateTransactionOutputWithValidScriptPubKey() {
        // Given a non-empty scriptPubKey is provided
        val validScriptPubKey = "validScriptPubKey"
        
        // When a TransactionOutput is created
        val transactionOutput = TransactionOutput(address = Address("validBitcoinAddress"), amountSats = 1000L, scriptPubKey = validScriptPubKey)
        
        // Then the TransactionOutput should be successfully created
        Assertions.assertNotNull(transactionOutput)
        Assertions.assertEquals(validScriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    @Test
    fun testCreateTransactionOutputWithEmptyScriptPubKey() {
        // Given an empty scriptPubKey is provided
        val emptyScriptPubKey = ""
        
        // When a TransactionOutput is created
        // Then an error should be thrown indicating invalid scriptPubKey
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address = Address("validBitcoinAddress"), amountSats = 1000L, scriptPubKey = emptyScriptPubKey)
        }
    }

}