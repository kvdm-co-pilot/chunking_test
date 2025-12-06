Feature: TransactionOutput Module
  Handles the output of a Bitcoin transaction

  @INFERRED @confidence-medium
  Scenario: Valid Transaction Output Creation
    Given a Bitcoin transaction is being processed
    When the transaction output is created
    Then the output should be valid according to Bitcoin protocol
    # Source: Domain knowledge of Bitcoin transaction output

  @INFERRED @confidence-medium
  Scenario: Transaction Output Verification
    Given a Bitcoin transaction output exists
    When the output is verified
    Then the verification should confirm the output is correct
    # Source: Domain knowledge of Bitcoin transaction output verification
