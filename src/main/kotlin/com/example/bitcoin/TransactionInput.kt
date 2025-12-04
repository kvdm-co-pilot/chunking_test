package com.example.bitcoin

data class TransactionInput(
    val sourceTxId: String,
    val sourceIndex: Int,
    val scriptSig: String
)
