Feature: TransactionInput Module
  Validates the initialization and constraints of the TransactionInput data class for Bitcoin transactions.

  @GROUNDED @confidence-high
  Scenario: Initialize TransactionInput with valid data
    Given a valid sourceTxId 'abc123'
    And a valid sourceIndex 0
    And a valid scriptSig '3045022100'
    When TransactionInput is initialized
    Then TransactionInput should be created successfully
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Reject TransactionInput with empty sourceTxId
    Given an empty sourceTxId ''
    And a valid sourceIndex 0
    And a valid scriptSig '3045022100'
    When TransactionInput is initialized
    Then initialization should fail with an error
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Reject TransactionInput with negative sourceIndex
    Given a valid sourceTxId 'abc123'
    And a negative sourceIndex -1
    And a valid scriptSig '3045022100'
    When TransactionInput is initialized
    Then initialization should fail with an error
    # Source: code_snippet

  @GROUNDED @confidence-high
  Scenario: Reject TransactionInput with empty scriptSig
    Given a valid sourceTxId 'abc123'
    And a valid sourceIndex 0
    And an empty scriptSig ''
    When TransactionInput is initialized
    Then initialization should fail with an error
    # Source: code_snippet

  @INFERRED @confidence-medium
  Scenario: Initialize TransactionInput with maximum valid sourceIndex
    Given a valid sourceTxId 'abc123'
    And a maximum valid sourceIndex 2147483647
    And a valid scriptSig '3045022100'
    When TransactionInput is initialized
    Then TransactionInput should be created successfully
    # Source: code_snippet

  @INFERRED @confidence-low
  Scenario: Reject TransactionInput with invalid scriptSig format
    Given a valid sourceTxId 'abc123'
    And a valid sourceIndex 0
    And an invalid scriptSig 'invalid_format'
    When TransactionInput is initialized
    Then initialization should fail with an error
    # Source: intent_summary

  @INFERRED @confidence-medium
  Scenario: Initialize TransactionInput with minimum valid sourceIndex
    Given a valid sourceTxId 'abc123'
    And a minimum valid sourceIndex 0
    And a valid scriptSig '3045022100'
    When TransactionInput is initialized
    Then TransactionInput should be created successfully
    # Source: code_snippet
