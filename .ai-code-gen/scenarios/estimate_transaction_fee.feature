Feature: Estimate Transaction Fee
  Calculates the estimated fee for a transaction based on the amount of satoshis and the satoshis per vbyte rate.

  @GROUNDED @confidence-high
  Scenario: Calculate fee with typical amountSats
    Given a transaction with amountSats of 5000
    When the fee is estimated
    Then the fee should be calculated as 200 * satsPerVbyte + 5
    # Source: Code line reference: return sizeVbytes * satsPerVbyte + (amountSats / 1000)

  @GROUNDED @confidence-high
  Scenario: Calculate fee with zero amountSats
    Given a transaction with amountSats of 0
    When the fee is estimated
    Then the fee should be calculated as 200 * satsPerVbyte
    # Source: Code line reference: return sizeVbytes * satsPerVbyte + (amountSats / 1000)

  @GROUNDED @confidence-high
  Scenario: Calculate fee with negative amountSats
    Given a transaction with amountSats of -1000
    When the fee is estimated
    Then the fee should be calculated as 200 * satsPerVbyte - 1
    # Source: Code line reference: return sizeVbytes * satsPerVbyte + (amountSats / 1000)

  @GROUNDED @confidence-high
  Scenario: Calculate fee with large amountSats
    Given a transaction with amountSats of 1000000
    When the fee is estimated
    Then the fee should be calculated as 200 * satsPerVbyte + 1000
    # Source: Code line reference: return sizeVbytes * satsPerVbyte + (amountSats / 1000)

  @GROUNDED @confidence-high
  Scenario: Ensure correct fee calculation with varying satsPerVbyte
    Given a transaction with amountSats of 5000 and satsPerVbyte of 2
    When the fee is estimated
    Then the fee should be calculated as 200 * 2 + 5
    # Source: Code line reference: return sizeVbytes * satsPerVbyte + (amountSats / 1000)
