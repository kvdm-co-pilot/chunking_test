package com.example.bitcoin

data class Utxo(
    val txId: String,
    val index: Int,
    val amountSats: Long,
    val address: Address
)
