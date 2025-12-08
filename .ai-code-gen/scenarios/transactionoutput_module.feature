Feature: TransactionOutput Module
  Validates the creation and properties of Bitcoin transaction outputs.

  @GROUNDED @confidence-high
  Scenario: Create TransactionOutput with valid address, amount, and scriptPubKey
    Given a valid Bitcoin address
    And a positive amount in satoshis
    And a valid scriptPubKey
    When TransactionOutput is created with these values
    Then TransactionOutput should be successfully created
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Handle TransactionOutput with zero amount
    Given a valid Bitcoin address
    And an amount of zero satoshis
    And a valid scriptPubKey
    When TransactionOutput is created with these values
    Then TransactionOutput should be successfully created
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Handle TransactionOutput with negative amount
    Given a valid Bitcoin address
    And a negative amount in satoshis
    And a valid scriptPubKey
    When TransactionOutput is created with these values
    Then TransactionOutput creation should fail
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Handle TransactionOutput with empty scriptPubKey
    Given a valid Bitcoin address
    And a positive amount in satoshis
    And an empty scriptPubKey
    When TransactionOutput is created with these values
    Then TransactionOutput creation should fail
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Handle TransactionOutput with null address
    Given a null Bitcoin address
    And a positive amount in satoshis
    And a valid scriptPubKey
    When TransactionOutput is created with these values
    Then TransactionOutput creation should fail
    # Source: code_snippet

  @EXPECTED @confidence-medium
  Scenario: Validate TransactionOutput properties
    Given a TransactionOutput is created
    When the properties are accessed
    Then the address should be valid
    And the amount should be a positive number
    And the scriptPubKey should be non-empty
    # Source: intent_summary
