package com.example.bitcoin

import java.security.SecureRandom

class PrivateKey private constructor(val keyHex: String) {
    companion object {
        private val secureRandom = SecureRandom()

        fun generate(): PrivateKey {
            val bytes = ByteArray(32)
            secureRandom.nextBytes(bytes)
            return PrivateKey(bytes.joinToString(separator = "") { "%02x".format(it) })
        }
    }

    fun toPublicKey(): PublicKey = PublicKey("pub_${keyHex.take(16)}")
}
