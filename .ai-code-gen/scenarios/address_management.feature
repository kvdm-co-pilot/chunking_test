Feature: Address Management
  Manage address details for users

  @INFERRED @confidence-medium
  Scenario: Add a new address
    Given user is on the address management page
    When user enters valid address details
    Then the address should be added successfully
    # Source: Domain knowledge of address management

  @INFERRED @confidence-medium
  Scenario: Fail to add address with missing details
    Given user is on the address management page
    When user enters address details with missing fields
    Then an error message should be displayed indicating missing information
    # Source: Domain knowledge of address management
