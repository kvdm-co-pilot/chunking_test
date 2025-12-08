Feature: Bitcoin Utxo Module
  Test scenarios for the Utxo data class representing Bitcoin transaction outputs.

  @GROUNDED @confidence-high
  Scenario: Create Utxo instance with valid data
    Given a valid transaction ID 'abc123'
    And a valid index 1
    And a valid amountSats 1000
    And a valid address object
    When a Utxo instance is created with these values
    Then the Utxo instance should be created successfully
    # Source: Utxo data class definition

  @GROUNDED @confidence-high
  Scenario: Access Utxo properties
    Given a Utxo instance is created with txId 'abc123', index 1, amountSats 1000, and a valid address
    When the properties of the Utxo instance are accessed
    Then the txId should be 'abc123'
    And the index should be 1
    And the amountSats should be 1000
    And the address should be the valid address object
    # Source: Utxo data class definition

  @INFERRED @confidence-medium
  Scenario: Handle null or empty txId
    Given a null or empty transaction ID
    When a Utxo instance is attempted to be created with this transaction ID
    Then an error should be thrown indicating invalid transaction ID
    # Source: Focus Areas

  @INFERRED @confidence-medium
  Scenario: Handle negative index
    Given a negative index value -1
    When a Utxo instance is attempted to be created with this index
    Then an error should be thrown indicating invalid index value
    # Source: Focus Areas

  @INFERRED @confidence-medium
  Scenario: Handle negative amountSats
    Given a negative amountSats value -1000
    When a Utxo instance is attempted to be created with this amountSats
    Then an error should be thrown indicating invalid amountSats value
    # Source: Focus Areas
