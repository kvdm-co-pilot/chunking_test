@Test
@Tag("bdd:create_utxo_instance_with_valid_data")
@Tag("triangulation:exact_scenario")
fun create_utxo_instance_with_valid_data() {
    // Given a valid txId 'abc123'
    val txId = "abc123"
    // And a valid index 0
    val index = 0
    // And a valid amountSats 1000
    val amountSats = 1000L
    // And a valid address object
    val address = Address("valid_address_object")
    
    // When a Utxo instance is created
    val utxo = Utxo(txId, index, amountSats, address)
    
    // Then the Utxo instance should be created successfully
    assertEquals(txId, utxo.txId)
    assertEquals(index, utxo.index)
    assertEquals(amountSats, utxo.amountSats)
    assertEquals(address, utxo.address)
}

// Grounding: Scenario: Create Utxo instance with valid data
// Given a valid txId 'abc123'
// And a valid index 0
// And a valid amountSats 1000
// And a valid address object
// When a Utxo instance is created
// Then the Utxo instance should be created successfully
// @GROUNDED