package com.example.bitcoin

data class Address(val value: String) {
    init {
        require(value.isNotBlank()) { "Address cannot be blank" }
        require(value.startsWith("bc1") || value.startsWith("1") || value.startsWith("3")) {
            "Address must be a valid Bitcoin address prefix"
        }
    }

    override fun toString() = value
}
