package com.example.bitcoin

class WalletService(private val feeEstimator: FeeEstimator) {
    private val wallets = mutableMapOf<String, Wallet>()

    fun createWallet(label: String): Wallet {
        val privateKey = PrivateKey.generate()
        val publicKey = privateKey.toPublicKey()
        val address = Address("bc1${publicKey.value.takeLast(20)}")
        val wallet = Wallet(
            id = "wallet-${wallets.size + 1}",
            label = label,
            address = address,
            publicKey = publicKey,
            utxos = mutableListOf(),
            balanceSats = 0
        )
        wallets[wallet.id] = wallet
        return wallet
    }

    fun receiveFunds(walletId: String, utxo: Utxo) {
        wallets[walletId]?.addUtxo(utxo)
        wallets[walletId]?.refreshBalance()
    }

    fun sendFunds(walletId: String, to: Address, amountSats: Long): Transaction {
        val wallet = wallets[walletId] ?: error("Wallet not found")
        val fee = feeEstimator.estimateFee(amountSats)
        val selectedUtxos = wallet.spendableUtxos(fee)
        if (selectedUtxos.isEmpty()) error("Insufficient spendable UTXOs")
        val inputs = selectedUtxos.map { TransactionInput(it.txId, it.index, "signature") }
        val outputs = listOf(TransactionOutput(to, amountSats, "script"))
        val change = selectedUtxos.sumOf { it.amountSats } - amountSats - fee
        if (change > 0) {
            outputs + TransactionOutput(wallet.address, change, "script")
        }
        wallet.utxos.removeAll(selectedUtxos)
        wallet.refreshBalance()
        return Transaction(
            id = "tx-${System.nanoTime()}",
            inputs = inputs,
            outputs = outputs,
            feeSats = fee
        )
    }
}
