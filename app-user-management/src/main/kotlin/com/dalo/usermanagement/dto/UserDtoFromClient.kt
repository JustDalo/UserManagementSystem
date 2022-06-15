package com.dalo.usermanagement.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlin.math.pow

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserDtoFromClient(
    val firstName: String,
    val lastName: String,
    var image: ByteArray = byteArrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as UserDtoFromClient

        if (
            !image.contentEquals(other.image) ||
            !firstName.contentEquals(other.firstName) ||
            !lastName.contentEquals(other.lastName)
        ) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        val hashSomething = 31.0
        var hash = firstName.hashCode()
        hash = (hash * hashSomething.pow(2)).toInt() + lastName.hashCode()
        hash = (hash * hashSomething).toInt() + image.contentHashCode()
        return hash
    }
}