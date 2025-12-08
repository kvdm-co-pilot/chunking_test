Feature: TransactionInput Module
  Validates and creates TransactionInput objects for Bitcoin transactions.

  @GROUNDED @confidence-high
  Scenario: Create TransactionInput with valid data
    Given a valid sourceTxId 'abc123'
    And a non-negative sourceIndex 0
    And a valid scriptSig '3045022100'
    When a TransactionInput object is created
    Then the object should be created successfully
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Create TransactionInput with empty sourceTxId
    Given an empty sourceTxId ''
    And a non-negative sourceIndex 0
    And a valid scriptSig '3045022100'
    When a TransactionInput object is created
    Then an error should be thrown indicating invalid sourceTxId
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Create TransactionInput with negative sourceIndex
    Given a valid sourceTxId 'abc123'
    And a negative sourceIndex -1
    And a valid scriptSig '3045022100'
    When a TransactionInput object is created
    Then an error should be thrown indicating invalid sourceIndex
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Create TransactionInput with empty scriptSig
    Given a valid sourceTxId 'abc123'
    And a non-negative sourceIndex 0
    And an empty scriptSig ''
    When a TransactionInput object is created
    Then an error should be thrown indicating invalid scriptSig
    # Source: code_snippet
