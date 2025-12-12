@Test
@Tag("bdd:create_transaction_output_with_valid_data")
@Tag("triangulation:exact_scenario")
fun create_transaction_output_with_valid_data() {
    // Given a valid Bitcoin address
    val address = Address("valid_bitcoin_address")
    // And a positive amount in satoshis
    val amountSats = 1000L
    // And a valid scriptPubKey
    val scriptPubKey = "valid_script_pub_key"
    
    // When TransactionOutput is created
    val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)
    
    // Then TransactionOutput should be successfully instantiated
    assertNotNull(transactionOutput)
    assertEquals(address, transactionOutput.address)
    assertEquals(amountSats, transactionOutput.amountSats)
    assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    
    // Grounding: GROUNDED (bdd_scenario)
}

@Test
@Tag("bdd:create_transaction_output_with_valid_data")
@Tag("triangulation:boundary")
fun transaction_output_with_zero_amount() {
    // Given a valid Bitcoin address and a valid scriptPubKey
    val address = "valid_bitcoin_address"
    val scriptPubKey = "valid_script_pub_key"
    
    // When TransactionOutput is created with zero amount
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address, 0, scriptPubKey)
    }
    
    // Then TransactionOutput should not be instantiated or should throw an error
    assertEquals("amountSats must be positive", exception.message)
    
    // Grounding: EXPECTED (code:amountSats must be positive)
}

@Test
@Tag("bdd:create_transaction_output_with_valid_data")
@Tag("triangulation:boundary")
fun transaction_output_with_negative_amount() {
    // Given a valid Bitcoin address
    val address = "valid_bitcoin_address"
    // And a negative amount in satoshis
    val amountSats = -1L
    // And a valid scriptPubKey
    val scriptPubKey = "valid_script_pub_key"

    // When TransactionOutput is created
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address, amountSats, scriptPubKey)
    }

    // Then TransactionOutput should not be instantiated or should throw an error
    assertEquals("amountSats must be positive", exception.message)
    // Grounding: EXPECTED (code:amountSats must be positive)
}


    @Test
    @Tag("bdd:handle_negative_amountsats")
    @Tag("triangulation:exact_scenario")
    fun handle_negative_amountsats_exact_scenario() {
        // Given a valid Bitcoin address
        val address = "valid_bitcoin_address"
        // And a negative amount in satoshis
        val amountSats = -1000L
        // And a valid scriptPubKey
        val scriptPubKey = "valid_script_pub_key"

        // When TransactionOutput is created
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }

        // Then TransactionOutput creation should fail
        assertEquals("Amount in satoshis cannot be negative", exception.message)
    }


import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Tag

@Tag("bdd:handle_negative_amountsats")
@Tag("triangulation:boundary")
@Test
fun handle_zero_amountsats_boundary() {
    // Given a valid Bitcoin address
    val address = "valid_bitcoin_address"
    // And an amount of zero satoshis
    val amountSats = 0L
    // And a valid scriptPubKey
    val scriptPubKey = "valid_script_pub_key"
    
    // When TransactionOutput is created
    // Then TransactionOutput creation should succeed
    assertDoesNotThrow {
        TransactionOutput(address, amountSats, scriptPubKey)
    }
    // Grounding: INFERRED (code:amountSats is a Long value)
}

@Test
@Tag("bdd:handle_negative_amountsats")
@Tag("triangulation:boundary")
fun handle_just_under_zero_amountsats_boundary() {
    // Given a valid Bitcoin address
    val address = "valid_bitcoin_address"
    
    // And a negative amount in satoshis
    val amountSats = -1L
    
    // And a valid scriptPubKey
    val scriptPubKey = "valid_script_pub_key"
    
    // When TransactionOutput is created
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address, amountSats, scriptPubKey)
    }
    
    // Then TransactionOutput creation should fail
    assertEquals("Amount in satoshis cannot be negative", exception.message)
    
    // Grounding: INFERRED (code:amountSats is a Long value)
}

@Test
@Tag("bdd:handle_zero_amountsats")
@Tag("triangulation:exact_scenario")
fun transaction_output_creation_with_zero_amountsats() {
    // Given a valid Bitcoin address
    val address = "valid_bitcoin_address"
    // And an amount of zero satoshis
    val amountSats = 0L
    // And a valid scriptPubKey
    val scriptPubKey = "valid_script_pub_key"

    // When TransactionOutput is created
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address, amountSats, scriptPubKey)
    }

    // Then TransactionOutput creation should fail
    assertEquals("Amount of satoshis cannot be zero", exception.message)
}

// Grounding: Based on the BDD scenario context provided

@Test
@Tag("bdd:handle_zero_amountsats")
@Tag("triangulation:boundary")
fun transaction_output_creation_with_negative_amountsats() {
    // Given a valid Bitcoin address
    val address = Address("valid_bitcoin_address")
    // And a negative amount of satoshis
    val amountSats = -1L
    // And a valid scriptPubKey
    val scriptPubKey = "valid_script_pub_key"

    // When TransactionOutput is created
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address, amountSats, scriptPubKey)
    }

    // Then TransactionOutput creation should fail
    assertEquals("Amount of satoshis cannot be negative", exception.message)
    // Grounding: INFERRED (code:amountSats_condition)
}

@Test
@Tag("bdd:handle_zero_amountsats")
@Tag("triangulation:boundary")
fun transaction_output_creation_with_positive_amountsats() {
    // Given a valid Bitcoin address
    val address = "valid_bitcoin_address"
    // And a positive amount of satoshis
    val amountSats = 1L
    // And a valid scriptPubKey
    val scriptPubKey = "valid_script_pub_key"

    // When TransactionOutput is created
    val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

    // Then TransactionOutput creation should succeed
    assertNotNull(transactionOutput)
    assertEquals(address, transactionOutput.address)
    assertEquals(amountSats, transactionOutput.amountSats)
    assertEquals(scriptPubKey, transactionOutput.scriptPubKey)

    // Grounding: INFERRED (code:amountSats_condition)
}

@Test
@Tag("bdd:handle_null_address")
@Tag("triangulation:exact_scenario")
fun handle_null_address_exact_scenario() {
    // Given a null Bitcoin address
    val address: Address? = null
    val amountSats = 1000L
    val scriptPubKey = "validScript"
    
    // When TransactionOutput is created
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address!!, amountSats, scriptPubKey)
    }
    
    // Then TransactionOutput creation should fail
    assertEquals("Address cannot be null", exception.message)
    
    // Grounding: GROUNDED (bdd_scenario)
}

    @Test
    @Tag("bdd:handle_null_address")
    @Tag("triangulation:boundary")
    fun handle_zero_amount_boundary() {
        // Given a valid Bitcoin address
        val address = "validAddress"
        // And a zero amount in satoshis
        val amountSats = 0L
        // And a valid scriptPubKey
        val scriptPubKey = "validScript"

        // When TransactionOutput is created
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }

        // Then TransactionOutput creation should fail
        assertEquals("amountSats must be positive", exception.message)
    }

@Test
@Tag("bdd:handle_null_address")
@Tag("triangulation:boundary")
fun handle_negative_amount_boundary() {
    // Given a valid Bitcoin address, a negative amount in satoshis, and a valid scriptPubKey
    val address = "validAddress"
    val amountSats = -1L
    val scriptPubKey = "validScript"

    // When TransactionOutput is created
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address, amountSats, scriptPubKey)
    }

    // Then TransactionOutput creation should fail
    assertEquals("amountSats must be positive", exception.message)
    // Grounding: INFERRED (code:amountSats must be positive)
}

@Test
@Tag("bdd:handle_null_address")
@Tag("triangulation:negative")
fun handle_invalid_scriptpubkey() {
    // Given a valid Bitcoin address and a positive amount in satoshis
    val address = "validAddress"
    val amountSats = 1000L
    
    // And an invalid scriptPubKey
    val scriptPubKey = "invalidScript"
    
    // When TransactionOutput is created
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address, amountSats, scriptPubKey)
    }
    
    // Then TransactionOutput creation should fail
    assertEquals("Invalid scriptPubKey", exception.message)
}