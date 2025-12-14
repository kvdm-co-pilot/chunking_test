Feature: Bitcoin Transaction Fee Estimation
  Estimate fees for Bitcoin transactions based on transaction size and amount in satoshis.

  @GROUNDED @confidence-high
  Scenario: Estimate fee with default satsPerVbyte
    Given default satsPerVbyte is 5
    When estimating fee for a transaction of 1000 satoshis
    Then fee should be calculated as 200 * 5 + (1000 / 1000)
    # Source: Code line reference: `private val satsPerVbyte: Long = 5`

  @GROUNDED @confidence-high
  Scenario: Estimate fee with specific amountSats
    Given satsPerVbyte is set to 5
    When estimating fee for a transaction of 5000 satoshis
    Then fee should be calculated as 200 * 5 + (5000 / 1000)
    # Source: Code line reference: `return sizeVbytes * satsPerVbyte + (amountSats / 1000)`

  @GROUNDED @confidence-high
  Scenario: Estimate fee with zero amountSats
    Given satsPerVbyte is set to 5
    When estimating fee for a transaction of 0 satoshis
    Then fee should be calculated as 200 * 5 + (0 / 1000)
    # Source: Code line reference: `return sizeVbytes * satsPerVbyte + (amountSats / 1000)`

  @GROUNDED @confidence-high
  Scenario: Estimate fee with negative amountSats
    Given satsPerVbyte is set to 5
    When estimating fee for a transaction of -1000 satoshis
    Then fee should be calculated as 200 * 5 + (-1000 / 1000)
    # Source: Code line reference: `return sizeVbytes * satsPerVbyte + (amountSats / 1000)`
