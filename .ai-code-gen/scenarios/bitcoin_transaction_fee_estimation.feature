Feature: Bitcoin Transaction Fee Estimation
  Estimate transaction fees based on satoshis per vbyte and transaction amount.

  @GROUNDED @confidence-high
  Scenario: Estimate fee with default sats per vbyte
    Given the default sats per vbyte is 5
    When a transaction amount of 1000 satoshis is provided
    Then the estimated fee should be 1005 satoshis
    # Source: Code line: `return sizeVbytes * satsPerVbyte + (amountSats / 1000)`

  @GROUNDED @confidence-high
  Scenario: Estimate fee for zero transaction amount
    Given the default sats per vbyte is 5
    When a transaction amount of 0 satoshis is provided
    Then the estimated fee should be 1000 satoshis
    # Source: Code line: `return sizeVbytes * satsPerVbyte + (amountSats / 1000)`

  @GROUNDED @confidence-high
  Scenario: Estimate fee for negative transaction amount
    Given the default sats per vbyte is 5
    When a transaction amount of -1000 satoshis is provided
    Then the estimated fee should be 999 satoshis
    # Source: Code line: `return sizeVbytes * satsPerVbyte + (amountSats / 1000)`

  @GROUNDED @confidence-high
  Scenario: Estimate fee for extremely large transaction amount
    Given the default sats per vbyte is 5
    When a transaction amount of 1000000000 satoshis is provided
    Then the estimated fee should be 1001000 satoshis
    # Source: Code line: `return sizeVbytes * satsPerVbyte + (amountSats / 1000)`

  @EXPECTED @confidence-medium
  Scenario: Estimate fee with adjusted sats per vbyte
    Given the sats per vbyte is adjusted to 10
    When a transaction amount of 2000 satoshis is provided
    Then the estimated fee should be 2002 satoshis
    # Source: intent_summary: Adjust fee based on varying transaction amounts
