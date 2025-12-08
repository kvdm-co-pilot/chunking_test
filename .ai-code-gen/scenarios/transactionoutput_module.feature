Feature: TransactionOutput Module
  Validates instantiation and data integrity for Bitcoin transaction outputs.

  @EXPECTED @confidence-medium
  Scenario: Instantiate TransactionOutput with valid data
    Given a valid Bitcoin address
    And a positive amount in satoshis
    And a valid scriptPubKey
    When TransactionOutput is instantiated
    Then the TransactionOutput should be created successfully
    # Source: intent_summary

  @EXPECTED @confidence-medium
  Scenario: Handle zero amountSats
    Given a valid Bitcoin address
    And an amount of zero satoshis
    And a valid scriptPubKey
    When TransactionOutput is instantiated
    Then the TransactionOutput should be created with zero amount
    # Source: intent_summary

  @EXPECTED @confidence-medium
  Scenario: Handle negative amountSats
    Given a valid Bitcoin address
    And a negative amount in satoshis
    And a valid scriptPubKey
    When TransactionOutput is instantiated
    Then an error should be raised indicating invalid amount
    # Source: intent_summary

  @EXPECTED @confidence-medium
  Scenario: Validate address and scriptPubKey formats
    Given an invalid Bitcoin address
    And a valid amount in satoshis
    And an invalid scriptPubKey
    When TransactionOutput is instantiated
    Then an error should be raised indicating invalid address or scriptPubKey
    # Source: intent_summary
