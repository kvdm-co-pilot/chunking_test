Feature: Utxo Module
  Validation and creation of Utxo data class for Bitcoin transaction outputs.

  @EXPECTED @confidence-medium
  Scenario: Create Utxo with valid data
    Given a valid txId, index, amountSats, and address
    When a Utxo object is created
    Then the Utxo object should be successfully created
    # Source: intent_summary

  @INFERRED @confidence-low
  Scenario: Handle Utxo with null txId
    Given a null txId
    When a Utxo object is created
    Then an error should be thrown indicating invalid txId
    # Source: Focus Areas

  @INFERRED @confidence-low
  Scenario: Handle Utxo with empty txId
    Given an empty txId
    When a Utxo object is created
    Then an error should be thrown indicating invalid txId
    # Source: Focus Areas

  @INFERRED @confidence-low
  Scenario: Handle Utxo with negative index
    Given a negative index
    When a Utxo object is created
    Then an error should be thrown indicating invalid index
    # Source: Focus Areas

  @INFERRED @confidence-low
  Scenario: Handle Utxo with negative amountSats
    Given a negative amountSats
    When a Utxo object is created
    Then an error should be thrown indicating invalid amountSats
    # Source: Focus Areas

  @INFERRED @confidence-low
  Scenario: Handle Utxo with null address
    Given a null address
    When a Utxo object is created
    Then an error should be thrown indicating invalid address
    # Source: Focus Areas

  @INFERRED @confidence-low
  Scenario: Create Utxo with maximum amountSats
    Given a valid txId, index, maximum amountSats, and address
    When a Utxo object is created
    Then the Utxo object should be successfully created
    # Source: Focus Areas

  @INFERRED @confidence-low
  Scenario: Create Utxo with minimum index
    Given a valid txId, minimum index, amountSats, and address
    When a Utxo object is created
    Then the Utxo object should be successfully created
    # Source: Focus Areas

  @INFERRED @confidence-low
  Scenario: Create Utxo with valid address format
    Given a valid txId, index, amountSats, and correctly formatted address
    When a Utxo object is created
    Then the Utxo object should be successfully created
    # Source: Focus Areas
