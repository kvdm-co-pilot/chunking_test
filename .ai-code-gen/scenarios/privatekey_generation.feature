Feature: PrivateKey Generation
  Generates a new PrivateKey instance with a random 32-byte value.

  @GROUNDED @confidence-high
  Scenario: Generate PrivateKey with 32-byte random value
    Given a request to generate a new PrivateKey
    When the generate method is invoked
    Then a 32-byte random value should be generated
    # Source: Code line: secureRandom.nextBytes(bytes)

  @GROUNDED @confidence-high
  Scenario: Convert byte array to hexadecimal string
    Given a 32-byte random value is generated
    When the byte array is converted to a string
    Then the resulting string should be in hexadecimal format
    # Source: Code line: bytes.joinToString(separator = "") { "%02x".format(it) }

  @GROUNDED @confidence-high
  Scenario: Create PrivateKey instance from hexadecimal string
    Given a hexadecimal string is generated from the byte array
    When a PrivateKey instance is created using the string
    Then the PrivateKey instance should be valid
    # Source: Code line: return PrivateKey(bytes.joinToString(separator = "") { "%02x".format(it) })

  @GROUNDED @confidence-high
  Scenario: Consistent behavior across multiple invocations
    Given multiple requests to generate new PrivateKeys
    When the generate method is invoked multiple times
    Then each generated PrivateKey should be unique
    # Source: Code line: secureRandom.nextBytes(bytes)
