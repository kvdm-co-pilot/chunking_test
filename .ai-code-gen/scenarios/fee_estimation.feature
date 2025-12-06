Feature: Fee Estimation
  Estimate fees for transactions based on given parameters

  @INFERRED @confidence-low
  Scenario: Estimate fee for standard transaction
    Given a standard transaction is initiated
    When the fee estimator calculates the fee
    Then the estimated fee should be within expected range for standard transactions
    # Source: Domain knowledge of fee estimation

  @INFERRED @confidence-low
  Scenario: Estimate fee for priority transaction
    Given a priority transaction is initiated
    When the fee estimator calculates the fee
    Then the estimated fee should be higher than standard transaction fees
    # Source: Domain knowledge of fee estimation
