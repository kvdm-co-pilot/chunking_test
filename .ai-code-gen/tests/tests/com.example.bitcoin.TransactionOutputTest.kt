    @Test
    @Tag("bdd:instantiate_transaction_output_with_valid_address_and_positive_amount_sats")
    @Tag("triangulation:exact_scenario")
    fun instantiate_transaction_output_with_valid_data() {
        // Given a valid Bitcoin address is provided
        val address = Address("valid_bitcoin_address")
        // And amountSats is greater than zero
        val amountSats = 1000L
        // And scriptPubKey is a valid script
        val scriptPubKey = "valid_script"

        // When TransactionOutput is instantiated
        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        // Then TransactionOutput should be created successfully
        assertNotNull(transactionOutput)
        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }
    // Grounding: GROUNDED (bdd_scenario)