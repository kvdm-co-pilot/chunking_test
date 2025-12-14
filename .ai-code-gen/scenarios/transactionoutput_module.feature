Feature: TransactionOutput Module
  Defines the TransactionOutput data class for Bitcoin transactions, focusing on address validation, amountSats constraints, and scriptPubKey handling.

  @GROUNDED @confidence-high
  Scenario: Instantiate TransactionOutput with valid values
    Given a valid Bitcoin address
    And a positive amountSats value
    And a valid scriptPubKey
    When TransactionOutput is instantiated
    Then the instance should be created successfully
    # Source: Code snippet - data class properties

  @EXPECTED @confidence-medium
  Scenario: Handle negative amountSats
    Given a valid Bitcoin address
    And a negative amountSats value
    And a valid scriptPubKey
    When TransactionOutput is instantiated
    Then an error should be thrown indicating invalid amountSats
    # Source: Focus Areas - Handling of negative amountSats

  @EXPECTED @confidence-medium
  Scenario: Handle zero amountSats
    Given a valid Bitcoin address
    And an amountSats value of zero
    And a valid scriptPubKey
    When TransactionOutput is instantiated
    Then the instance should be created successfully
    # Source: Focus Areas - Handling of zero amountSats

  @EXPECTED @confidence-medium
  Scenario: Handle extremely large amountSats
    Given a valid Bitcoin address
    And an extremely large amountSats value
    And a valid scriptPubKey
    When TransactionOutput is instantiated
    Then the instance should be created successfully
    # Source: Focus Areas - Handling of extremely large amountSats

  @EXPECTED @confidence-medium
  Scenario: Handle null address
    Given a null Bitcoin address
    And a positive amountSats value
    And a valid scriptPubKey
    When TransactionOutput is instantiated
    Then an error should be thrown indicating invalid address
    # Source: Focus Areas - Null address

  @EXPECTED @confidence-medium
  Scenario: Handle empty scriptPubKey
    Given a valid Bitcoin address
    And a positive amountSats value
    And an empty scriptPubKey
    When TransactionOutput is instantiated
    Then an error should be thrown indicating invalid scriptPubKey
    # Source: Focus Areas - Empty scriptPubKey
