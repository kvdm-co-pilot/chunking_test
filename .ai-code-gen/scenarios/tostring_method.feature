Feature: toString Method
  Convert an object to its string representation

  @INFERRED @confidence-medium
  Scenario: Convert object to string representation
    Given an object is created
    When toString method is called on the object
    Then the method should return a string representation of the object
    # Source: Common behavior of toString methods

  @INFERRED @confidence-medium
  Scenario: Handle null object gracefully
    Given a null object is provided
    When toString method is called on the null object
    Then the method should return 'null' as the string representation
    # Source: Best practice for handling null values in toString methods
