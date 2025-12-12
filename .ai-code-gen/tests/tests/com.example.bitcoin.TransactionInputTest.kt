@Test
@Tag("bdd:create_transaction_input_with_valid_data")
@Tag("triangulation:exact_scenario")
fun create_transaction_input_with_valid_data() {
    // Given a valid sourceTxId 'abc123'
    val sourceTxId = "abc123"
    // And a valid sourceIndex 1
    val sourceIndex = 1
    // And a valid scriptSig 'signature'
    val scriptSig = "signature"

    // When TransactionInput is created
    val transactionInput = TransactionInput(sourceTxId, sourceIndex, scriptSig)

    // Then TransactionInput should be successfully instantiated
    assertNotNull(transactionInput)
    assertEquals(sourceTxId, transactionInput.sourceTxId)
    assertEquals(sourceIndex, transactionInput.sourceIndex)
    assertEquals(scriptSig, transactionInput.scriptSig)

    // Grounding: EXPECTED (bdd_scenario)
}