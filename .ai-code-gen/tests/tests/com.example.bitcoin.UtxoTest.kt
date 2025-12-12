@Test
@Tag("bdd:create_utxo_with_valid_data")
@Tag("triangulation:exact_scenario")
fun create_utxo_with_valid_data() {
    // Given a valid txId 'abc123'
    val txId = "abc123"
    // And a valid index 1
    val index = 1
    // And a valid amountSats 1000
    val amountSats = 1000L
    // And a valid address object
    val address = Address("valid_address_object")
    
    // When Utxo is created with these values
    val utxo = Utxo(txId, index, amountSats, address)
    
    // Then Utxo should be successfully created
    assertEquals(txId, utxo.txId)
    assertEquals(index, utxo.index)
    assertEquals(amountSats, utxo.amountSats)
    assertEquals(address, utxo.address)
    
    // Grounding: GROUNDED (bdd_scenario)
}

@Test
@Tag("bdd:create_utxo_with_valid_data")
@Tag("triangulation:boundary")
fun create_utxo_with_index_0() {
    // Given
    val txId = "abc123"
    val index = 0
    val amountSats = 1000L
    val address = Address("valid_address_object")

    // When
    val utxo = Utxo(txId, index, amountSats, address)

    // Then
    assertNotNull(utxo)
    assertEquals(txId, utxo.txId)
    assertEquals(index, utxo.index)
    assertEquals(amountSats, utxo.amountSats)
    assertEquals(address, utxo.address)

    // Grounding: index must be a non-negative integer
}

@Test
@Tag("bdd:create_utxo_with_valid_data")
@Tag("triangulation:boundary")
fun create_utxo_with_amount_0() {
    // Given a valid txId 'abc123'
    val txId = "abc123"
    // And a valid index 1
    val index = 1
    // And a valid amountSats 0
    val amountSats = 0L
    // And a valid address object
    val address = Address("valid_address_object")

    // When Utxo is created with these values
    val utxo = Utxo(txId, index, amountSats, address)

    // Then Utxo should be successfully created
    assertNotNull(utxo)
    assertEquals(txId, utxo.txId)
    assertEquals(index, utxo.index)
    assertEquals(amountSats, utxo.amountSats)
    assertEquals(address, utxo.address)
    // Grounding: amountSats must be a non-negative long
}

@Test
@Tag("bdd:create_utxo_with_valid_data")
@Tag("triangulation:negative")
fun create_utxo_with_negative_amount() {
    // Given a valid txId 'abc123'
    val txId = "abc123"
    // And a valid index 1
    val index = 1
    // And a negative amountSats -1
    val amountSats = -1L
    // And a valid address object
    val address = Address("valid_address_object")
    
    // When Utxo is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        Utxo(txId, index, amountSats, address)
    }
    
    // Then Utxo creation should fail
    assertEquals("amountSats must be a non-negative long", exception.message)
    
    // Grounding: INFERRED (code:amountSats must be a non-negative long)
}

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("bdd:create_utxo_with_negative_index")
@Tag("triangulation:exact_scenario")
@Test
fun create_utxo_with_negative_index_should_fail() {
    // Given a valid txId 'abc123'
    val txId = "abc123"
    // And a negative index -1
    val index = -1
    // And a valid amountSats 1000
    val amountSats = 1000L
    // And a valid address object
    val address = Address("valid_address_object")

    // When Utxo is created with these values
    // Then Utxo creation should fail
    assertThrows(IllegalArgumentException::class.java) {
        Utxo(txId, index, amountSats, address)
    }
    // Grounding: GROUNDED
}

@Test
@Tag("bdd:create_utxo_with_negative_index")
@Tag("triangulation:boundary")
fun create_utxo_with_zero_index_should_succeed() {
    // Given a valid txId 'abc123'
    val txId = "abc123"
    // And a boundary index 0
    val index = 0
    // And a valid amountSats 1000
    val amountSats = 1000L
    // And a valid address object
    val address = Address("valid_address_object")
    
    // When Utxo is created with these values
    val utxo = Utxo(txId, index, amountSats, address)
    
    // Then Utxo creation should succeed
    assertNotNull(utxo)
    assertEquals(txId, utxo.txId)
    assertEquals(index, utxo.index)
    assertEquals(amountSats, utxo.amountSats)
    assertEquals(address, utxo.address)
    
    // Grounding: INFERRED (code:boundary_value_analysis)
}

@Test
@Tag("bdd:create_utxo_with_negative_index")
@Tag("triangulation:boundary")
fun create_utxo_with_positive_index_should_succeed() {
    // Given a valid txId 'abc123'
    val txId = "abc123"
    // And a positive index 1
    val index = 1
    // And a valid amountSats 1000
    val amountSats = 1000L
    // And a valid address object
    val address = Address("valid_address_object")
    
    // When Utxo is created with these values
    val utxo = Utxo(txId, index, amountSats, address)
    
    // Then Utxo creation should succeed
    assertNotNull(utxo)
    assertEquals(txId, utxo.txId)
    assertEquals(index, utxo.index)
    assertEquals(amountSats, utxo.amountSats)
    assertEquals(address, utxo.address)
    
    // Grounding: INFERRED (code:boundary_value_analysis)
}

@Test
@Tag("bdd:create_utxo_with_negative_index")
@Tag("triangulation:negative")
fun create_utxo_with_invalid_txid_should_fail() {
    // Given
    val txId = ""
    val index = 1
    val amountSats = 1000L
    val address = Address("valid_address_object")

    // When
    val exception = assertThrows<IllegalArgumentException> {
        Utxo(txId, index, amountSats, address)
    }

    // Then
    assertEquals("Invalid txId", exception.message)
    // Grounding: INFERRED (code:validation_of_txId)
}

@Test
@Tag("bdd:create_utxo_with_negative_index")
@Tag("triangulation:negative")
fun create_utxo_with_negative_amount_should_fail() {
    // Given a valid txId 'abc123'
    val txId = "abc123"
    // And a valid index 1
    val index = 1
    // And a negative amountSats -1000
    val amountSats = -1000L
    // And a valid address object
    val address = Address("valid_address_object")

    // When Utxo is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        Utxo(txId, index, amountSats, address)
    }

    // Then Utxo creation should fail
    assertEquals("amountSats must be positive", exception.message)
    // Grounding: INFERRED (code:validation_of_amountSats)
}

@Test
@Tag("bdd:create_utxo_with_negative_amountsats")
@Tag("triangulation:exact_scenario")
fun create_utxo_with_negative_amountsats() {
    // Given a valid txId 'abc123'
    val txId = "abc123"
    // And a valid index 1
    val index = 1
    // And a negative amountSats -1000
    val amountSats = -1000L
    // And a valid address object
    val address = Address("valid_address_object")

    // When Utxo is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        Utxo(txId, index, amountSats, address)
    }

    // Then Utxo creation should fail
    assertEquals("amountSats cannot be negative", exception.message)
    // Grounding: Validates that Utxo creation fails with negative amountSats
}

@Test
@Tag("bdd:create_utxo_with_negative_amountsats")
@Tag("triangulation:boundary")
fun create_utxo_with_zero_amountsats() {
    // Given a valid txId 'abc123'
    val txId = "abc123"
    // And a valid index 1
    val index = 1
    // And a zero amountSats
    val amountSats = 0L
    // And a valid address object
    val address = Address("valid_address_object")
    
    // When Utxo is created with these values
    val utxo = Utxo(txId, index, amountSats, address)
    
    // Then Utxo creation should succeed
    assertNotNull(utxo)
    assertEquals(txId, utxo.txId)
    assertEquals(index, utxo.index)
    assertEquals(amountSats, utxo.amountSats)
    assertEquals(address, utxo.address)
    
    // Grounding: INFERRED (code:amountSats should be non-negative)
}


    @Test
    @Tag("bdd:create_utxo_with_negative_amountsats")
    @Tag("triangulation:boundary")
    fun create_utxo_with_negative_one_amountsats() {
        // Given a valid txId 'abc123'
        val txId = "abc123"
        // And a valid index 1
        val index = 1
        // And a negative amountSats -1
        val amountSats = -1L
        // And a valid address object
        val address = Address("valid_address_object")
        
        // When Utxo is created with these values
        val exception = assertThrows<IllegalArgumentException> {
            Utxo(txId, index, amountSats, address)
        }
        
        // Then Utxo creation should fail
        assertEquals("amountSats should be non-negative", exception.message)
    }


@Test
@Tag("bdd:create_utxo_with_negative_amountsats")
@Tag("triangulation:error_verification")
fun verify_error_handling_for_negative_amountsats() {
    // Given a valid txId 'abc123'
    val txId = "abc123"
    // And a valid index 1
    val index = 1
    // And a negative amountSats -1000
    val amountSats = -1000L
    // And a valid address object
    val address = Address("valid_address_object")

    // When Utxo is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        Utxo(txId, index, amountSats, address)
    }

    // Then Utxo creation should fail
    assertEquals("Invalid amountSats", exception.message)
    // # @GROUNDED
}

@Test
@Tag("bdd:create_utxo_with_empty_txid")
@Tag("triangulation:exact_scenario")
fun create_utxo_with_empty_txid() {
    // Given an empty txId
    val txId = ""
    // And a valid index
    val index = 1
    // And a valid amountSats
    val amountSats = 1000L
    // And a valid address object
    val address = Address("valid_address_object")
    
    // When Utxo is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        Utxo(txId, index, amountSats, address)
    }
    
    // Then Utxo creation should fail
    assertEquals("txId cannot be empty", exception.message)
    
    // Grounding: GROUNDED (bdd_scenario)
}

@Test
@Tag("bdd:create_utxo_with_empty_txid")
@Tag("triangulation:boundary")
fun create_utxo_with_zero_index() {
    // Given a valid txId, index at lower boundary (0), valid amountSats, and valid address object
    val txId = "valid_txid"
    val index = 0
    val amountSats = 1000L
    val address = Address("valid_address_object")
    
    // When Utxo is created with these values
    val utxo = Utxo(txId, index, amountSats, address)
    
    // Then Utxo creation should succeed
    assertNotNull(utxo)
    assertEquals(txId, utxo.txId)
    assertEquals(index, utxo.index)
    assertEquals(amountSats, utxo.amountSats)
    assertEquals(address, utxo.address)
    
    // Grounding: index should be a valid integer
}


    @Test
    @Tag("bdd:create_utxo_with_empty_txid")
    @Tag("triangulation:boundary")
    fun create_utxo_with_max_index() {
        // Given a valid txId, max_int index, valid amountSats, and valid address
        val txId = "valid_txid"
        val index = Int.MAX_VALUE
        val amountSats = 1000L
        val address = Address("valid_address_object")

        // When Utxo is created with these values
        val utxo = Utxo(txId, index, amountSats, address)

        // Then Utxo creation should succeed
        assertNotNull(utxo)
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }


@Test
@Tag("bdd:create_utxo_with_empty_txid")
@Tag("triangulation:boundary")
fun create_utxo_with_zero_amount() {
    // Given a valid txId, index, and address object
    val txId = "valid_txid"
    val index = 1
    val address = Address("valid_address_object")
    
    // When Utxo is created with amountSats at its lower boundary (0)
    val exception = assertThrows<IllegalArgumentException> {
        Utxo(txId, index, 0, address)
    }
    
    // Then Utxo creation should fail
    assertEquals("amountSats must be greater than zero", exception.message)
    
    // Grounding: INFERRED (code:amountSats should be a valid long)
}

@Test
@Tag("bdd:create_utxo_with_empty_txid")
@Tag("triangulation:boundary")
fun create_utxo_with_max_amount() {
    // Given
    val txId = "valid_txid"
    val index = 1
    val amountSats = Long.MAX_VALUE
    val address = Address("valid_address_object")
    
    // When
    val utxo = Utxo(txId, index, amountSats, address)
    
    // Then
    assertNotNull(utxo)
    assertEquals(txId, utxo.txId)
    assertEquals(index, utxo.index)
    assertEquals(amountSats, utxo.amountSats)
    assertEquals(address, utxo.address)
    
    // Grounding: amountSats should be a valid long
}

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Tag

@Tag("bdd:create_utxo_with_empty_txid")
@Tag("triangulation:negative")
@Test
fun create_utxo_with_invalid_address() {
    // Given
    val txId = "valid_txid"
    val index = 1
    val amountSats = 1000
    val invalidAddress = "invalid_address_object" // Grounding: address should be a valid object

    // When/Then
    assertThrows<IllegalArgumentException> {
        Utxo(txId, index, amountSats, invalidAddress)
    }
}

@Test
@Tag("bdd:create_utxo_with_empty_txid")
@Tag("triangulation:error_verification")
fun verify_error_message_for_empty_txid() {
    // Given an empty txId ''
    val txId = ""
    val index = 1
    val amountSats = 1000L
    val address = Address("valid_address_object")

    // When Utxo is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        Utxo(txId, index, amountSats, address)
    }

    // Then Utxo creation should fail
    assertEquals("txId cannot be empty", exception.message)
    // @GROUNDED
}

@Test
@Tag("bdd:create_utxo_with_null_address")
@Tag("triangulation:exact_scenario")
fun create_utxo_with_null_address_should_fail() {
    // Given
    val txId = "abc123"
    val index = 1
    val amountSats = 1000L
    val address: Address? = null

    // When
    val exception = assertThrows<IllegalArgumentException> {
        Utxo(txId, index, amountSats, address!!)
    }

    // Then
    assertEquals("Address must not be null", exception.message)
    // Grounding: Validates that Utxo creation fails when address is null
}

@Test
@Tag("bdd:create_utxo_with_null_address")
@Tag("triangulation:boundary")
fun create_utxo_with_index_0() {
    // Given a valid txId 'abc123'
    val txId = "abc123"
    // And a valid index 0
    val index = 0
    // And a valid amountSats 1000
    val amountSats = 1000L
    // And a valid address 'valid_address'
    val address = Address("valid_address")

    // When Utxo is created with these values
    val utxo = Utxo(txId, index, amountSats, address)

    // Then Utxo creation should succeed
    assertNotNull(utxo)
    assertEquals(txId, utxo.txId)
    assertEquals(index, utxo.index)
    assertEquals(amountSats, utxo.amountSats)
    assertEquals(address, utxo.address)
    // Grounding: Utxo object requires valid index
}

@Test
@Tag("bdd:create_utxo_with_null_address")
@Tag("triangulation:boundary")
fun create_utxo_with_amount_0() {
    // Given a valid txId 'abc123'
    val txId = "abc123"
    // And a valid index 1
    val index = 1
    // And a valid amountSats 0
    val amountSats = 0L
    // And a valid address
    val address = "valid_address"
    
    // When Utxo is created with these values
    val exception = assertThrows<IllegalArgumentException> {
        Utxo(txId, index, amountSats, address)
    }
    
    // Then Utxo creation should fail
    assertEquals("AmountSats must be positive", exception.message)
    
    // Grounding: INFERRED (code:Utxo object requires positive amountSats)
}

@Test
@Tag("bdd:create_utxo_with_null_address")
@Tag("triangulation:negative")
fun create_utxo_with_null_txid() {
    // Given
    val index = 1
    val amountSats = 1000L
    val address = "valid_address"
    val txId: String? = null

    // When
    val exception = assertThrows<IllegalArgumentException> {
        Utxo(txId = txId!!, index = index, amountSats = amountSats, address = address)
    }

    // Then
    assertEquals("txId cannot be null", exception.message)
    // Grounding: Utxo object requires non-null txId
}

import org.junit.jupiter.api.Assertions.assertThrows

@Tag("bdd:create_utxo_with_null_address")
@Tag("triangulation:error_verification")
fun verify_error_handling_for_null_address() {
    // Given a valid txId 'abc123'
    val txId = "abc123"
    // And a valid index 1
    val index = 1
    // And a valid amountSats 1000
    val amountSats = 1000L
    // And a null address
    val address: Address? = null

    // When Utxo is created with these values
    // Then Utxo creation should fail
    val exception = assertThrows<IllegalArgumentException> {
        Utxo(txId, index, amountSats, address!!)
    }

    // Assert that the error message indicates address cannot be null
    assert(exception.message!!.contains("address cannot be null"))
}

// Grounding: Utxo object requires non-null address


    @Test
    @Tag("bdd:create_utxo_with_all_valid_fields")
    @Tag("triangulation:exact_scenario")
    fun create_utxo_with_valid_fields() {
        // Given a valid txId 'abc123'
        val txId = "abc123"
        
        // And a valid index 1
        val index = 1
        
        // And a valid amountSats 1000
        val amountSats = 1000L
        
        // And a valid address object
        val address = Address("valid_address_object")
        
        // When Utxo is created with these values
        val utxo = Utxo(txId, index, amountSats, address)
        
        // Then Utxo should be successfully created
        assertNotNull(utxo)
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }
    // Grounding: Scenario: Create Utxo with all valid fields
    // Given a valid txId 'abc123'
    // And a valid index 1
    // And a valid amountSats 1000
    // And a valid address object
    // When Utxo is created with these values
    // Then Utxo should be successfully created
    // @GROUNDED


@Test
@Tag("bdd:create_utxo_with_all_valid_fields")
@Tag("triangulation:boundary")
fun create_utxo_with_empty_txId() {
    // Given
    val txId = ""
    val index = 1
    val amountSats = 1000L
    val address = Address("valid_address_object")

    // When/Then
    val exception = assertThrows<IllegalArgumentException> {
        Utxo(txId, index, amountSats, address)
    }

    assertEquals("txId must be a non-empty string", exception.message)
    // Grounding: EXPECTED (code:txId must be a non-empty string)
}

@Test
@Tag("bdd:create_utxo_with_all_valid_fields")
@Tag("triangulation:boundary")
fun create_utxo_with_negative_index() {
    // Given
    val txId = "abc123"
    val index = -1
    val amountSats = 1000L
    val address = Address("valid_address_object")

    // When/Then
    val exception = assertThrows<IllegalArgumentException> {
        Utxo(txId, index, amountSats, address)
    }
    assertEquals("index must be a non-negative integer", exception.message)
    // Grounding: EXPECTED (code:index must be a non-negative integer)
}

@Test
@Tag("bdd:create_utxo_with_all_valid_fields")
@Tag("triangulation:boundary")
fun create_utxo_with_zero_amountSats() {
    // Given
    val txId = "abc123"
    val index = 1
    val amountSats = 0L
    val address = Address("valid_address_object")

    // When & Then
    val exception = assertThrows<IllegalArgumentException> {
        Utxo(txId, index, amountSats, address)
    }

    assertEquals("amountSats must be a positive long", exception.message)
    // Grounding: EXPECTED (code:amountSats must be a positive long)
}

@Test
@Tag("bdd:create_utxo_with_all_valid_fields")
@Tag("triangulation:boundary")
fun create_utxo_with_null_address() {
    // Given
    val txId = "abc123"
    val index = 1
    val amountSats = 1000
    val address = null

    // When & Then
    assertThrows<IllegalArgumentException> {
        Utxo(txId, index, amountSats, address)
    }.apply {
        assertEquals("address must be a valid Address object", message)
    }
    // Grounding: EXPECTED (code:address must be a valid Address object)
}