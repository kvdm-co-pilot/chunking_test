Feature: Bitcoin Address String Representation
  Ensures the toString method returns the correct Bitcoin address value

  @GROUNDED @confidence-high
  Scenario: Return Bitcoin address value as string
    Given a Bitcoin address object with a non-null value
    When toString method is called
    Then the method should return the Bitcoin address value as a string
    # Source: Code line reference: override fun toString() = value

  @INFERRED @confidence-low
  Scenario: Handle null or empty Bitcoin address value
    Given a Bitcoin address object with a null or empty value
    When toString method is called
    Then the method should return an empty string
    # Source: Focus Areas: Validate behavior when the Bitcoin address value is null or empty
