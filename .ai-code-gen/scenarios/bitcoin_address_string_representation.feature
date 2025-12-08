Feature: Bitcoin Address String Representation
  Ensures the toString method returns the correct string representation of the Bitcoin address.

  @GROUNDED @confidence-high
  Scenario: Return Bitcoin address value as string
    Given Bitcoin address value is '1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa'
    When toString method is called
    Then it should return '1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa'
    # Source: Code line: override fun toString() = value

  @INFERRED @confidence-medium
  Scenario: Handle null Bitcoin address value
    Given Bitcoin address value is null
    When toString method is called
    Then it should return 'null'
    # Source: Focus Areas: Verify behavior when the Bitcoin address value is null or empty
