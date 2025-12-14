Feature: Fee Estimation
  Estimate Bitcoin transaction fees based on satoshis per vbyte.

  @GROUNDED @confidence-high
  Scenario: Estimate fee with default satsPerVbyte and positive amountSats
    Given default satsPerVbyte is set to 5
    When user estimates fee for amountSats of 10000
    Then fee should be calculated as 200 * 5 + (10000 / 1000)
    # Source: Code line reference: return sizeVbytes * satsPerVbyte + (amountSats / 1000)

  @GROUNDED @confidence-high
  Scenario: Estimate fee with zero amountSats
    Given default satsPerVbyte is set to 5
    When user estimates fee for amountSats of 0
    Then fee should be calculated as 200 * 5 + (0 / 1000)
    # Source: Code line reference: return sizeVbytes * satsPerVbyte + (amountSats / 1000)

  @GROUNDED @confidence-high
  Scenario: Estimate fee with negative amountSats
    Given default satsPerVbyte is set to 5
    When user estimates fee for amountSats of -1000
    Then fee should be calculated as 200 * 5 + (-1000 / 1000)
    # Source: Code line reference: return sizeVbytes * satsPerVbyte + (amountSats / 1000)

  @GROUNDED @confidence-high
  Scenario: Estimate fee with positive amountSats
    Given default satsPerVbyte is set to 5
    When user estimates fee for amountSats of 5000
    Then fee should be calculated as 200 * 5 + (5000 / 1000)
    # Source: Code line reference: return sizeVbytes * satsPerVbyte + (amountSats / 1000)
