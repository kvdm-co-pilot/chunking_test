import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Assertions.assertDoesNotThrow

@Tag("bdd:initialize_with_valid_bitcoin_address_prefix_bc1")
@Tag("triangulation:exact_scenario")
@Test
fun initialize_with_valid_bc1_prefix() {
    // Given: an Address value 'bc1xyz'
    val addressValue = "bc1xyz"
    
    // When: the Address is initialized
    val addressInitialization = { Address(addressValue) }
    
    // Then: the initialization should succeed
    assertDoesNotThrow(addressInitialization)
}

// Grounding: Validates initialization with a valid Bitcoin address prefix 'bc1'