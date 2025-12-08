Feature: TransactionInput Module
  Validates creation and properties of TransactionInput data class for Bitcoin transactions

  @GROUNDED @confidence-high
  Scenario: Create TransactionInput with valid data
    Given a valid sourceTxId 'abc123'
    And a valid sourceIndex 0
    And a valid scriptSig 'signature'
    When TransactionInput is created
    Then TransactionInput should be successfully created
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Handle null sourceTxId
    Given a null sourceTxId
    And a valid sourceIndex 0
    And a valid scriptSig 'signature'
    When TransactionInput is created
    Then an error should be thrown
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Handle empty sourceTxId
    Given an empty sourceTxId ''
    And a valid sourceIndex 0
    And a valid scriptSig 'signature'
    When TransactionInput is created
    Then an error should be thrown
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Handle negative sourceIndex
    Given a valid sourceTxId 'abc123'
    And a negative sourceIndex -1
    And a valid scriptSig 'signature'
    When TransactionInput is created
    Then an error should be thrown
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Handle null scriptSig
    Given a valid sourceTxId 'abc123'
    And a valid sourceIndex 0
    And a null scriptSig
    When TransactionInput is created
    Then an error should be thrown
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Handle empty scriptSig
    Given a valid sourceTxId 'abc123'
    And a valid sourceIndex 0
    And an empty scriptSig ''
    When TransactionInput is created
    Then an error should be thrown
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Create TransactionInput with edge case values
    Given a valid sourceTxId 'abc123'
    And a sourceIndex 0
    And a scriptSig 'edge-case-signature'
    When TransactionInput is created
    Then TransactionInput should be successfully created
    # Source: code_snippet
