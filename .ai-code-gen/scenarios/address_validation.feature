Feature: Address Validation
  Validation of Bitcoin address format during initialization

  @GROUNDED @confidence-high
  Scenario: Initialize with valid non-blank address
    Given an address value 'bc1abc'
    When the Address class is initialized with this value
    Then the initialization should succeed
    # Source: Code line 2: require(value.isNotBlank())

  @GROUNDED @confidence-high
  Scenario: Reject blank address input
    Given a blank address value ''
    When the Address class is initialized with this value
    Then an error 'Address cannot be blank' should be thrown
    # Source: Code line 2: require(value.isNotBlank())

  @GROUNDED @confidence-high
  Scenario: Validate Bitcoin address prefix starting with 'bc1'
    Given an address value 'bc1xyz'
    When the Address class is initialized with this value
    Then the initialization should succeed
    # Source: Code line 3: require(value.startsWith("bc1") || value.startsWith("1") || value.startsWith("3"))

  @GROUNDED @confidence-high
  Scenario: Reject address with invalid Bitcoin prefix
    Given an address value 'abcxyz'
    When the Address class is initialized with this value
    Then an error 'Address must be a valid Bitcoin address prefix' should be thrown
    # Source: Code line 3: require(value.startsWith("bc1") || value.startsWith("1") || value.startsWith("3"))
