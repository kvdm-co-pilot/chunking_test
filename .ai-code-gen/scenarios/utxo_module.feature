Feature: UTXO Module
  Testing basic functionality of the UTXO module

  @INFERRED @confidence-medium
  Scenario: Validate UTXO creation
    Given a new transaction is created
    When the transaction outputs are processed
    Then a UTXO should be created for each output
    # Source: Domain knowledge of UTXO functionality

  @INFERRED @confidence-medium
  Scenario: Check UTXO update after transaction
    Given an existing UTXO is available
    When a transaction spends the UTXO
    Then the UTXO should be marked as spent
    # Source: Domain knowledge of UTXO functionality
