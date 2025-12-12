    @Test
    @Tag("bdd:estimate_fee_with_default_satspervbyte_and_positive_amountsats")
    @Tag("triangulation:exact_scenario")
    fun estimate_fee_with_default_satspervbyte_and_positive_amountsats() {
        // Given the default satsPerVbyte is 5
        val feeEstimator = FeeEstimator()
        
        // When the user estimates fee for amountSats of 1000
        val fee = feeEstimator.estimateFee(1000)
        
        // Then the fee should be calculated as 200 * 5 + (1000 / 1000)
        assertEquals(1005, fee)
    }
    
    // Grounding: GROUNDED (bdd_scenario)