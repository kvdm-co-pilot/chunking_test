Feature: Bitcoin Transaction Fee Estimation
  Estimate Bitcoin transaction fees based on transaction size and sats per vbyte.

  @GROUNDED @confidence-high
  Scenario: Estimate fee with default sats per vbyte
    Given a transaction amount of 1000 sats
    When the fee is estimated using default sats per vbyte
    Then the estimated fee should be 1005 sats
    # Source: Code line: 'private val satsPerVbyte: Long = 5'

  @GROUNDED @confidence-high
  Scenario: Estimate fee with custom sats per vbyte
    Given a transaction amount of 2000 sats
    And sats per vbyte set to 10
    When the fee is estimated
    Then the estimated fee should be 2020 sats
    # Source: Code line: 'return sizeVbytes * satsPerVbyte + (amountSats / 1000)'

  @GROUNDED @confidence-high
  Scenario: Estimate fee with zero transaction amount
    Given a transaction amount of 0 sats
    When the fee is estimated using default sats per vbyte
    Then the estimated fee should be 1000 sats
    # Source: Code line: 'return sizeVbytes * satsPerVbyte + (amountSats / 1000)'

  @INFERRED @confidence-medium
  Scenario: Estimate fee with negative transaction amount
    Given a transaction amount of -1000 sats
    When the fee is estimated using default sats per vbyte
    Then the estimated fee should be 995 sats
    # Source: Focus Areas: Handle zero and negative transaction amounts
