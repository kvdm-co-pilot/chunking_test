Feature: Address Class
  Validates the Address class functionality

  @INFERRED @confidence-medium
  Scenario: Create Address with valid data
    Given a user has valid address data
    When the user creates an Address object
    Then the Address object should be created successfully
    # Source: Domain knowledge of address validation

  @INFERRED @confidence-medium
  Scenario: Create Address with invalid data
    Given a user has invalid address data
    When the user attempts to create an Address object
    Then the Address object creation should fail
    # Source: Domain knowledge of address validation
