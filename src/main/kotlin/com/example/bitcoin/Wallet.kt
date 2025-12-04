package com.example.bitcoin

data class Wallet(
    val id: String,
    val label: String,
    val address: Address,
    val publicKey: PublicKey,
    val utxos: MutableList<Utxo>,
    var balanceSats: Long
) {
    fun refreshBalance(): Long {
        balanceSats = utxos.sumOf { it.amountSats }
        return balanceSats
    }

    fun addUtxo(utxo: Utxo) = utxos.add(utxo)

    fun spendableUtxos(minerFee: Long): List<Utxo> = utxos.filter { it.amountSats > minerFee }
}
