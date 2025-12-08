Feature: Bitcoin Address Validation
  Validates Bitcoin address format during initialization of Address class.

  @GROUNDED @confidence-high
  Scenario: Initialization with a valid Bitcoin address prefix 'bc1'
    Given a user wants to create an Address
    When the user provides a Bitcoin address starting with 'bc1'
    Then the Address should be initialized successfully
    # Source: Code line 3-6

  @GROUNDED @confidence-high
  Scenario: Rejection of blank address input
    Given a user wants to create an Address
    When the user provides a blank address
    Then an error should be thrown with message 'Address cannot be blank'
    # Source: Code line 3

  @GROUNDED @confidence-high
  Scenario: Rejection of address with invalid prefix
    Given a user wants to create an Address
    When the user provides an address starting with 'abc'
    Then an error should be thrown with message 'Address must be a valid Bitcoin address prefix'
    # Source: Code line 4-6

  @GROUNDED @confidence-high
  Scenario: Boundary testing with minimal valid prefix '1'
    Given a user wants to create an Address
    When the user provides a Bitcoin address starting with '1'
    Then the Address should be initialized successfully
    # Source: Code line 4-6
