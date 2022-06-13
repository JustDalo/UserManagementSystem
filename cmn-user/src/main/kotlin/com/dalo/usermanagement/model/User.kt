package com.dalo.usermanagement.model

import lombok.Getter
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.GenerationType
import javax.persistence.GeneratedValue
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "users", schema = "users")
@Getter
class User(
    @NotBlank
    @Column(name = "usr_first_name")
    private var firstName: String,
    @NotBlank
    @Column(name = "usr_last_name")
    private var lastName: String,
    @Column(name = "usr_role")
    private var roldId: Long,
    @Column(name = "usr_image")
    private var image: ByteArray
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private var id: Long = -1

    constructor() : this("", "", 1, byteArrayOf())

    fun getId(): Long {
        return id
    }

    fun getFirstName(): String {
        return firstName
    }

    fun setFirstName(firstName: String) {
        this.firstName = firstName
    }

    fun getLastName(): String {
        return lastName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
    }

    fun getImage(): ByteArray {
        return image
    }
}