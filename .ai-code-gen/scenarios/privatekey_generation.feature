Feature: PrivateKey Generation
  Generates a new PrivateKey instance with a random 32-byte value.

  @GROUNDED @confidence-high
  Scenario: Generate PrivateKey with 32-byte random value
    Given secureRandom is available
    When generate method is called
    Then a 32-byte random value should be generated
    # Source: Code line: secureRandom.nextBytes(bytes)

  @GROUNDED @confidence-high
  Scenario: Convert byte array to hexadecimal string
    Given a byte array of length 32
    When byte array is converted to a string
    Then the string should be a 64-character hexadecimal representation
    # Source: Code line: bytes.joinToString(separator = "") { "%02x".format(it) }

  @GROUNDED @confidence-high
  Scenario: Create PrivateKey instance from hexadecimal string
    Given a valid hexadecimal string
    When PrivateKey is instantiated with the string
    Then a PrivateKey instance should be created successfully
    # Source: Code line: return PrivateKey(bytes.joinToString(separator = "") { "%02x".format(it) })

  @INFERRED @confidence-medium
  Scenario: Handle secureRandom failure
    Given secureRandom fails to generate bytes
    When generate method is called
    Then an error should be thrown
    # Source: Focus Areas: Test handling of secureRandom exceptions

  @GROUNDED @confidence-high
  Scenario: Validate byte array length
    Given generate method is called
    When byte array is created
    Then the byte array should have a length of 32
    # Source: Code line: val bytes = ByteArray(32)

  @GROUNDED @confidence-high
  Scenario: Confirm hexadecimal string format
    Given a byte array is converted to a string
    When hexadecimal format is applied
    Then the format should match the expected hexadecimal pattern
    # Source: Code line: bytes.joinToString(separator = "") { "%02x".format(it) }
