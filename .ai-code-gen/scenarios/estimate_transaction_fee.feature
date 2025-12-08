Feature: Estimate Transaction Fee
  Calculates the estimated fee for a transaction based on the amount of satoshis and the satoshis per vbyte rate.

  @GROUNDED @confidence-high
  Scenario: Calculate fee with zero amountSats
    Given the transaction amount is 0 satoshis
    When the fee is estimated
    Then the fee should be equal to 200 times the satsPerVbyte rate
    # Source: Code line reference: return sizeVbytes * satsPerVbyte + (amountSats / 1000)

  @GROUNDED @confidence-high
  Scenario: Calculate fee with negative amountSats
    Given the transaction amount is -1000 satoshis
    When the fee is estimated
    Then the fee should be less than 200 times the satsPerVbyte rate
    # Source: Code line reference: return sizeVbytes * satsPerVbyte + (amountSats / 1000)

  @GROUNDED @confidence-high
  Scenario: Calculate fee with large amountSats
    Given the transaction amount is 1000000 satoshis
    When the fee is estimated
    Then the fee should be greater than 200 times the satsPerVbyte rate
    # Source: Code line reference: return sizeVbytes * satsPerVbyte + (amountSats / 1000)

  @GROUNDED @confidence-high
  Scenario: Calculate fee with typical amountSats
    Given the transaction amount is 50000 satoshis
    When the fee is estimated
    Then the fee should be equal to 200 times the satsPerVbyte rate plus 50
    # Source: Code line reference: return sizeVbytes * satsPerVbyte + (amountSats / 1000)
