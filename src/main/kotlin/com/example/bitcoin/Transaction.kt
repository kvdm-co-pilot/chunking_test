package com.example.bitcoin

data class Transaction(
    val id: String,
    val inputs: List<TransactionInput>,
    val outputs: List<TransactionOutput>,
    val feeSats: Long
) {
    val totalInput: Long = inputs.sumOf { it.sourceIndex.toLong() } // placeholder
    val totalOutput: Long = outputs.sumOf { it.amountSats }
}
