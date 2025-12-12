import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Assertions.assertEquals
import com.example.bitcoin.TransactionOutput
import com.example.bitcoin.Address

@Tag("bdd:create_transaction_output_with_valid_address_amountsats_and_scriptpubkey")
@Tag("triangulation:exact_scenario")
@Test
fun create_transaction_output_with_valid_inputs() {
    // Given a valid Bitcoin address
    val address = Address("valid_bitcoin_address")
    // And a positive amount in satoshis
    val amountSats = 1000L
    // And a non-empty scriptPubKey
    val scriptPubKey = "valid_script_pub_key"
    
    // When TransactionOutput is created with these values
    val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)
    
    // Then the TransactionOutput should be created successfully
    assertEquals(address, transactionOutput.address)
    assertEquals(amountSats, transactionOutput.amountSats)
    assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    // Grounding: This test is based on the BDD scenario provided
}


    @Test
    @Tag("bdd:create_transaction_output_with_valid_address_amountsats_and_scriptpubkey")
    @Tag("triangulation:boundary")
    fun create_transaction_output_with_zero_amountsats() {
        // Given a valid Bitcoin address, zero amountSats, and a valid scriptPubKey
        val address = "valid_bitcoin_address"
        val amountSats = 0L
        val scriptPubKey = "valid_script_pub_key"

        // When attempting to create a TransactionOutput
        // Then the creation should fail because amountSats must be positive
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
        assertEquals("amountSats must be positive", exception.message)
    }


import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Tag

@Tag("bdd:create_transaction_output_with_valid_address_amountsats_and_scriptpubkey")
@Tag("triangulation:boundary")
@Test
fun create_transaction_output_with_negative_amountsats() {
    // Given a valid Bitcoin address, negative amountSats, and valid scriptPubKey
    val address = "valid_bitcoin_address"
    val amountSats = -1L
    val scriptPubKey = "valid_script_pub_key"

    // When TransactionOutput is created with these values
    // Then TransactionOutput creation should fail
    assertThrows(IllegalArgumentException::class.java) {
        TransactionOutput(address, amountSats, scriptPubKey)
    }
    // Grounding: amountSats must be positive
}

@Test
@Tag("bdd:create_transaction_output_with_valid_address_amountsats_and_scriptpubkey")
@Tag("triangulation:boundary")
fun create_transaction_output_with_empty_scriptpubkey() {
    // Given a valid Bitcoin address, a positive amount in satoshis, and an empty scriptPubKey
    val address = "valid_bitcoin_address"
    val amountSats = 1000L
    val scriptPubKey = ""

    // When attempting to create a TransactionOutput with these values
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address, amountSats, scriptPubKey)
    }

    // Then the TransactionOutput creation should fail
    assertEquals("scriptPubKey must be non-empty", exception.message)
    // Grounding: INFERRED (code:scriptPubKey must be non-empty)
}

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("bdd:create_transaction_output_with_valid_address_amountsats_and_scriptpubkey")
@Tag("triangulation:negative")
@Test
fun create_transaction_output_with_invalid_address() {
    // Given an invalid Bitcoin address
    val invalidAddress = "invalid_bitcoin_address"
    val amountSats = 1000L
    val scriptPubKey = "valid_script_pub_key"

    // When creating a TransactionOutput with these values
    // Then the TransactionOutput creation should fail
    assertThrows<IllegalArgumentException> {
        TransactionOutput(Address(invalidAddress), amountSats, scriptPubKey)
    }
    // Grounding: INFERRED (code:address must be valid)
}

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Tag

@Tag("bdd:create_transaction_output_with_invalid_address")
@Tag("triangulation:exact_scenario")
@Test
fun create_transaction_output_with_invalid_address() {
    // Given an invalid Bitcoin address
    val invalidAddress = "invalid_address"
    val amountSats = 1000L
    val scriptPubKey = "valid_script"

    // When TransactionOutput is created with these values
    // Then the creation should fail with an invalid address error
    assertThrows(IllegalArgumentException::class.java) {
        TransactionOutput(Address(invalidAddress), amountSats, scriptPubKey)
    }
}

// Grounding: EXPECTED (bdd_scenario)
// Source: com.example.bitcoin.TransactionOutput

@Test
@Tag("bdd:create_transaction_output_with_invalid_address")
@Tag("triangulation:boundary")
fun create_transaction_output_with_zero_amount() {
    // Given a valid Bitcoin address
    val address = Address("valid_address")
    // And a zero amount in satoshis
    val amountSats = 0L
    // And a valid scriptPubKey
    val scriptPubKey = "valid_script"

    // When TransactionOutput is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address, amountSats, scriptPubKey)
    }

    // Then the creation should fail or handle zero amount
    assertEquals("amountSats must be positive", exception.message)
}

@Test
@Tag("bdd:create_transaction_output_with_invalid_address")
@Tag("triangulation:boundary")
fun create_transaction_output_with_negative_amount() {
    // Given a valid Bitcoin address, a negative amount in satoshis, and a valid scriptPubKey
    val address = "valid_address"
    val amountSats = -1L
    val scriptPubKey = "valid_script"

    // When TransactionOutput is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address, amountSats, scriptPubKey)
    }

    // Then the creation should fail with a negative amount error
    assertEquals("amountSats must be positive", exception.message)
    // Grounding: INFERRED (code:amountSats must be positive)
}

@Test
@Tag("bdd:create_transaction_output_with_invalid_address")
@Tag("triangulation:boundary")
fun create_transaction_output_with_empty_script_pub_key() {
    // Given a valid Bitcoin address
    val address = "valid_address"
    // And a positive amount in satoshis
    val amountSats = 1000L
    // And an empty scriptPubKey
    val scriptPubKey = ""
    
    // When TransactionOutput is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address, amountSats, scriptPubKey)
    }
    
    // Then the creation should fail with an empty scriptPubKey error
    assertEquals("scriptPubKey must be non-empty", exception.message)
    
    // Grounding: INFERRED (code:scriptPubKey must be non-empty)
}

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("bdd:create_transaction_output_with_invalid_address")
@Tag("triangulation:negative")
@Test
fun create_transaction_output_with_invalid_address_format() {
    // Given an invalid Bitcoin address
    val invalidAddress = "12345"
    val amountSats = 1000L
    val scriptPubKey = "valid_script"

    // When TransactionOutput is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(Address(invalidAddress), amountSats, scriptPubKey)
    }

    // Then the creation should fail with an invalid address error
    assert(exception.message?.contains("invalid address") == true)
    // Grounding: INFERRED (code:address must be valid)
}

@Test
@Tag("bdd:create_transaction_output_with_invalid_address")
@Tag("triangulation:error_verification")
fun verify_invalid_address_error_message() {
    // Given an invalid Bitcoin address
    val invalidAddress = "invalid_address"
    val amountSats = 1000L
    val scriptPubKey = "valid_script"

    // When TransactionOutput is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(Address(invalidAddress), amountSats, scriptPubKey)
    }

    // Then the creation should fail with an invalid address error
    assertEquals("Invalid Bitcoin address", exception.message)
}

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Tag

@Tag("bdd:create_transaction_output_with_negative_amount_sats")
@Tag("triangulation:exact_scenario")
@Test
fun create_transaction_output_with_negative_amount_sats() {
    // Given a valid Bitcoin address
    val address = "valid_bitcoin_address"
    // And a negative amount in satoshis
    val amountSats = -1000L
    // And a non-empty scriptPubKey
    val scriptPubKey = "non_empty_script"

    // When TransactionOutput is created with these values
    // Then the creation should fail with a negative amount error
    assertThrows(IllegalArgumentException::class.java) {
        TransactionOutput(address, amountSats, scriptPubKey)
    }
}

// Grounding: EXPECTED (bdd_scenario)

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("bdd:create_transaction_output_with_negative_amount_sats")
@Tag("triangulation:boundary")
@Test
fun create_transaction_output_with_zero_amount_sats() {
    // Given a valid Bitcoin address and a non-empty scriptPubKey
    val address = "valid_bitcoin_address"
    val scriptPubKey = "non_empty_script"
    val amountSats = 0L

    // When TransactionOutput is created with zero amountSats
    // Then the creation should fail or handle zero amount gracefully
    assertThrows(IllegalArgumentException::class.java) {
        TransactionOutput(address, amountSats, scriptPubKey)
    }
    // Grounding: INFERRED (code:amountSats should be positive)
}

@Test
@Tag("bdd:create_transaction_output_with_negative_amount_sats")
@Tag("triangulation:boundary")
fun create_transaction_output_with_minimum_positive_amount_sats() {
    // Given a valid Bitcoin address
    val address = "valid_bitcoin_address"
    // And a minimum positive amount in satoshis
    val amountSats = 1L
    // And a non-empty scriptPubKey
    val scriptPubKey = "non_empty_script"

    // When TransactionOutput is created with these values
    val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

    // Then the creation should succeed
    assertNotNull(transactionOutput)
    assertEquals(address, transactionOutput.address)
    assertEquals(amountSats, transactionOutput.amountSats)
    assertEquals(scriptPubKey, transactionOutput.scriptPubKey)

    // Grounding: INFERRED (code:amountSats should be positive)
}

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Tag

@Tag("bdd:create_transaction_output_with_negative_amount_sats")
@Tag("triangulation:error_verification")
@Test
fun verify_negative_amount_error_message() {
    // Given a valid Bitcoin address
    val address = "valid_bitcoin_address"
    // And a negative amount in satoshis
    val amountSats = -1000L
    // And a non-empty scriptPubKey
    val scriptPubKey = "non_empty_script"

    // When TransactionOutput is created with these values
    val exception = assertThrows(IllegalArgumentException::class.java) {
        TransactionOutput(address, amountSats, scriptPubKey)
    }

    // Then the creation should fail with a negative amount error
    assert(exception.message?.contains("negative amount") == true)
}

@Test
@Tag("bdd:create_transaction_output_with_empty_scriptpubkey")
@Tag("triangulation:exact_scenario")
fun create_transaction_output_with_empty_scriptpubkey() {
    // Given a valid Bitcoin address
    val address = Address("valid_bitcoin_address")
    // And a positive amount in satoshis
    val amountSats = 1000L
    // And an empty scriptPubKey
    val scriptPubKey = ""

    // When TransactionOutput is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address, amountSats, scriptPubKey)
    }

    // Then the creation should fail with an empty scriptPubKey error
    assertEquals("scriptPubKey cannot be empty", exception.message)
}

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("bdd:create_transaction_output_with_empty_scriptpubkey")
@Tag("triangulation:boundary")
@Test
fun create_transaction_output_with_zero_amount() {
    // Given a valid Bitcoin address and a zero amount in satoshis
    val address = "valid_bitcoin_address"
    val amountSats = 0L
    val scriptPubKey = "valid_scriptPubKey"

    // When TransactionOutput is attempted to be created with these values
    // Then the creation should fail or handle zero amount appropriately
    assertThrows(IllegalArgumentException::class.java) {
        TransactionOutput(address, amountSats, scriptPubKey)
    }
    // Grounding: inferred from code: amountSats condition
}


@Tag("bdd:create_transaction_output_with_empty_scriptpubkey")
@Tag("triangulation:boundary")
@Test
fun create_transaction_output_with_negative_amount() {
    // Given a valid Bitcoin address and a negative amount in satoshis
    val address = "valid_bitcoin_address"
    val amountSats = -1L
    val scriptPubKey = "valid_scriptPubKey"

    // When TransactionOutput is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address, amountSats, scriptPubKey)
    }

    // Then the creation should fail with a negative amount error
    assertEquals("Amount must be non-negative", exception.message)
}



    @Test
    @Tag("bdd:create_transaction_output_with_empty_scriptpubkey")
    @Tag("triangulation:error_verification")
    fun create_transaction_output_with_empty_scriptpubkey_error_handling() {
        // Given a valid Bitcoin address and a positive amount in satoshis
        val address = "valid_bitcoin_address"
        val amountSats = 1000L
        val scriptPubKey = ""

        // When TransactionOutput is created with these values
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }

        // Then the creation should fail with an empty scriptPubKey error
        assertEquals("scriptPubKey cannot be empty", exception.message)
    }


@Test
@Tag("bdd:create_transaction_output_with_zero_amount_sats")
@Tag("triangulation:exact_scenario")
fun create_transaction_output_with_zero_amount_sats() {
    // Given a valid Bitcoin address
    val address = "valid_bitcoin_address"
    // And an amount of zero satoshis
    val amountSats = 0L
    // And a non-empty scriptPubKey
    val scriptPubKey = "non_empty_script_pub_key"

    // When TransactionOutput is created with these values
    val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

    // Then the TransactionOutput should be created successfully
    assertNotNull(transactionOutput)
    assertEquals(address, transactionOutput.address)
    assertEquals(amountSats, transactionOutput.amountSats)
    assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
}

@Test
@Tag("bdd:create_transaction_output_with_zero_amount_sats")
@Tag("triangulation:boundary")
fun create_transaction_output_with_negative_amount_sats() {
    // Given a valid Bitcoin address
    val address = "valid_bitcoin_address"
    // And a negative amount of satoshis
    val amountSats = -1L
    // And a non-empty scriptPubKey
    val scriptPubKey = "non_empty_script_pub_key"
    
    // When TransactionOutput is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address, amountSats, scriptPubKey)
    }
    
    // Then the TransactionOutput creation should fail or reject input
    assertEquals("Amount of satoshis cannot be negative", exception.message)
    
    // Grounding: INFERRED (code:absence_of_conditions)
}

@Test
@Tag("bdd:create_transaction_output_with_zero_amount_sats")
@Tag("triangulation:boundary")
fun create_transaction_output_with_max_amount_sats() {
    // Given a valid Bitcoin address
    val address = Address("valid_bitcoin_address")
    // And an amount of maximum satoshis
    val amountSats = Long.MAX_VALUE
    // And a non-empty scriptPubKey
    val scriptPubKey = "non_empty_script_pub_key"
    
    // When TransactionOutput is created with these values
    val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)
    
    // Then the TransactionOutput should be created successfully
    assertNotNull(transactionOutput)
    assertEquals(address, transactionOutput.address)
    assertEquals(amountSats, transactionOutput.amountSats)
    assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    
    // Grounding: INFERRED (code:absence_of_conditions)
}

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("bdd:create_transaction_output_with_maximum_amount_sats")
@Tag("triangulation:exact_scenario")
@Test
fun create_transaction_output_with_maximum_amount_sats() {
    // Given a valid Bitcoin address
    val address = Address("valid_bitcoin_address")
    // And the maximum possible amount in satoshis
    val amountSats = Long.MAX_VALUE
    // And a non-empty scriptPubKey
    val scriptPubKey = "non_empty_script"
    
    // When TransactionOutput is created with these values
    val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)
    
    // Then the TransactionOutput should be created successfully
    assertNotNull(transactionOutput)
    // Grounding: Validates creation of TransactionOutput with maximum amountSats
}

@Test
@Tag("bdd:create_transaction_output_with_maximum_amount_sats")
@Tag("triangulation:boundary")
fun create_transaction_output_with_zero_amount_sats() {
    // Given a valid Bitcoin address
    val address = Address("valid_bitcoin_address")
    // And zero amount in satoshis
    val amountSats = 0L
    // And a non-empty scriptPubKey
    val scriptPubKey = "non_empty_script"
    
    // When TransactionOutput is created with these values
    val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)
    
    // Then the TransactionOutput should be created successfully
    assertNotNull(transactionOutput)
    assertEquals(address, transactionOutput.address)
    assertEquals(amountSats, transactionOutput.amountSats)
    assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    
    // Grounding: amountSats must be a valid Long value
}

@Test
@Tag("bdd:create_transaction_output_with_maximum_amount_sats")
@Tag("triangulation:boundary")
fun create_transaction_output_with_negative_amount_sats() {
    // Given a valid Bitcoin address
    val address = Address("valid_bitcoin_address")
    
    // And a negative amount in satoshis
    val amountSats = -1L
    
    // And a non-empty scriptPubKey
    val scriptPubKey = "non_empty_script"
    
    // When TransactionOutput is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        TransactionOutput(address, amountSats, scriptPubKey)
    }
    
    // Then the TransactionOutput creation should fail
    assertEquals("amountSats must be a valid Long value", exception.message)
    
    // Grounding: INFERRED (code:amountSats must be a valid Long value)
}

    @Test
    @Tag("bdd:create_transaction_output_with_maximum_amount_sats")
    @Tag("triangulation:negative")
    fun create_transaction_output_with_invalid_address() {
        // Given an invalid Bitcoin address
        val invalidAddress = "invalid_bitcoin_address"
        val amountSats = Long.MAX_VALUE
        val scriptPubKey = "non_empty_script"

        // When attempting to create a TransactionOutput
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(Address(invalidAddress), amountSats, scriptPubKey)
        }

        // Then the TransactionOutput creation should fail
        assertEquals("Invalid address", exception.message)
    }

    // Grounding: Address must be valid

    @Test
    @Tag("bdd:create_transaction_output_with_maximum_amount_sats")
    @Tag("triangulation:negative")
    fun create_transaction_output_with_empty_script_pub_key() {
        // Given a valid Bitcoin address
        val address = Address("valid_bitcoin_address")
        // And the maximum possible amount in satoshis
        val amountSats = Long.MAX_VALUE
        // And an empty scriptPubKey
        val scriptPubKey = ""

        // When TransactionOutput is created with these values
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }

        // Then the TransactionOutput creation should fail
        assertEquals("scriptPubKey must be non-empty", exception.message)
    }