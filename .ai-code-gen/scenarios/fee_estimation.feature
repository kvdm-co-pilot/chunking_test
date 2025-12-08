Feature: Fee Estimation
  Estimating Bitcoin transaction fees based on satoshis per vbyte.

  @GROUNDED @confidence-high
  Scenario: Estimate fee with default satsPerVbyte
    Given the default satsPerVbyte is set to 5
    When the fee is estimated for an amount of 1000 satoshis
    Then the estimated fee should be 1005 satoshis
    # Source: code_snippet line 3

  @GROUNDED @confidence-high
  Scenario: Estimate fee with zero amountSats
    Given the default satsPerVbyte is set to 5
    When the fee is estimated for an amount of 0 satoshis
    Then the estimated fee should be 1000 satoshis
    # Source: code_snippet line 5

  @GROUNDED @confidence-high
  Scenario: Estimate fee with negative amountSats
    Given the default satsPerVbyte is set to 5
    When the fee is estimated for an amount of -1000 satoshis
    Then the estimated fee should be 995 satoshis
    # Source: code_snippet line 5

  @INFERRED @confidence-medium
  Scenario: Estimate fee with zero satsPerVbyte
    Given the satsPerVbyte is set to 0
    When the fee is estimated for an amount of 1000 satoshis
    Then the estimated fee should be 1 satoshi
    # Source: Focus Areas from code analysis
