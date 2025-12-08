Feature: Convert Object to String
  Ensure the toString method correctly converts objects to their string representation.

  @INFERRED @confidence-medium
  Scenario: Convert simple object to string
    Given an object with basic properties
    When the toString method is called on the object
    Then the method should return a string representation of the object
    # Source: Domain knowledge of toString method behavior

  @INFERRED @confidence-medium
  Scenario: Convert complex object to string
    Given an object with nested properties
    When the toString method is called on the object
    Then the method should return a string representation that includes all nested properties
    # Source: Domain knowledge of toString method behavior
