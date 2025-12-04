package com.example.bitcoin

data class TransactionOutput(
    val address: Address,
    val amountSats: Long,
    val scriptPubKey: String
)
