com.example.bitcoin

org.junit.jupiter.api.Assertions
org.junit.jupiter.api.Test

class UtxoTest {

    @Test
    @Test
    fun testCreateUtxoWithValidData() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = 1000L
        val address = Address("validAddress")
        
        // When
        val utxo = Utxo(txId, index, amountSats, address)
        
        // Then
        Assertions.assertNotNull(utxo)
        Assertions.assertEquals(txId, utxo.txId)
        Assertions.assertEquals(index, utxo.index)
        Assertions.assertEquals(amountSats, utxo.amountSats)
        Assertions.assertEquals(address, utxo.address)
    }

    @Test
    @Test
    fun testHandleUtxoCreationWithEmptyTxId() {
        // Given
        val txId = ""
        val index = 0
        val amountSats = 1000L
        val address = Address("validAddress")
        
        // When/Then
        val exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address)
        }
        Assertions.assertEquals("Invalid txId", exception.message)
    }

    @Test
    @Test
    fun testHandleUtxoCreationWithNullTxId() {
        // Given
        val txId: String? = null
        val index = 0
        val amountSats = 1000L
        val address = Address("validAddress")
        
        // When/Then
        val exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId!!, index, amountSats, address)
        }
        Assertions.assertEquals("Invalid txId", exception.message)
    }

    @Test
    @Test
    fun testHandleUtxoCreationWithNegativeIndex() {
        // Given
        val txId = "abc123"
        val index = -1
        val amountSats = 1000L
        val address = Address("validAddress")
        
        // When/Then
        val exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address)
        }
        Assertions.assertEquals("Invalid index", exception.message)
    }

    @Test
    @Test
    fun testHandleUtxoCreationWithNegativeAmountSats() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = -1000L
        val address = Address("validAddress")
        
        // When/Then
        val exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address)
        }
        Assertions.assertEquals("Invalid amountSats", exception.message)
    }

    @Test
    @Test
    fun testHandleUtxoCreationWithNullAddress() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = 1000L
        val address: Address? = null
        
        // When/Then
        val exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address!!)
        }
        Assertions.assertEquals("Invalid address", exception.message)
    }

}