Feature: Bitcoin Utxo Module
  Tests for the Utxo data class representing Bitcoin transaction outputs.

  @GROUNDED @confidence-high
  Scenario: Create Utxo with valid data
    Given a valid txId 'abc123'
    And a valid index 0
    And a valid amountSats 1000000
    And a valid address object
    When a Utxo instance is created
    Then the Utxo instance should encapsulate the provided data
    # Source: Code snippet - Utxo data class fields

  @GROUNDED @confidence-high
  Scenario: Create Utxo with null txId
    Given a null txId
    And a valid index 0
    And a valid amountSats 1000000
    And a valid address object
    When a Utxo instance is created
    Then an error should be thrown indicating invalid txId
    # Source: Code snippet - Utxo data class fields

  @GROUNDED @confidence-high
  Scenario: Create Utxo with negative index
    Given a valid txId 'abc123'
    And a negative index -1
    And a valid amountSats 1000000
    And a valid address object
    When a Utxo instance is created
    Then an error should be thrown indicating invalid index
    # Source: Code snippet - Utxo data class fields

  @GROUNDED @confidence-high
  Scenario: Create Utxo with negative amountSats
    Given a valid txId 'abc123'
    And a valid index 0
    And a negative amountSats -1000000
    And a valid address object
    When a Utxo instance is created
    Then an error should be thrown indicating invalid amountSats
    # Source: Code snippet - Utxo data class fields
