Feature: Bitcoin Address String Representation
  Ensures the toString method returns the correct Bitcoin address value.

  @GROUNDED @confidence-high
  Scenario: Return Bitcoin address as string
    Given Bitcoin address object is initialized with a valid address
    When toString method is called
    Then the method should return the Bitcoin address as a string
    # Source: Code line reference: override fun toString() = value

  @INFERRED @confidence-medium
  Scenario: Handle null Bitcoin address value
    Given Bitcoin address object is initialized with a null value
    When toString method is called
    Then the method should return null
    # Source: Focus Areas: Handle null value gracefully

  @INFERRED @confidence-medium
  Scenario: Handle empty string Bitcoin address value
    Given Bitcoin address object is initialized with an empty string
    When toString method is called
    Then the method should return an empty string
    # Source: Focus Areas: Handle empty string value
