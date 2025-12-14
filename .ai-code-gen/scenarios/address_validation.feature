Feature: Address Validation
  Validates Bitcoin address format upon initialization

  @GROUNDED @confidence-high
  Scenario: Initialization with a valid non-blank address
    Given an Address instance is being created
    When the address value is 'bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh'
    Then the Address instance should be initialized successfully
    # Source: Code line 2-3

  @GROUNDED @confidence-high
  Scenario: Handling of blank address input
    Given an Address instance is being created
    When the address value is an empty string
    Then an IllegalArgumentException should be thrown with message 'Address cannot be blank'
    # Source: Code line 3

  @GROUNDED @confidence-high
  Scenario: Validation for address starting with 'bc1'
    Given an Address instance is being created
    When the address value is 'bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh'
    Then the Address instance should be initialized successfully
    # Source: Code line 4-5

  @GROUNDED @confidence-high
  Scenario: Handling of address with invalid prefix
    Given an Address instance is being created
    When the address value is 'xyzqxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh'
    Then an IllegalArgumentException should be thrown with message 'Address must be a valid Bitcoin address prefix'
    # Source: Code line 4-5
