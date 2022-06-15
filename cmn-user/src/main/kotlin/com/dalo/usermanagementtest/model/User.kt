package com.dalo.usermanagementtest.model

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.RequiredArgsConstructor
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
@AllArgsConstructor
@RequiredArgsConstructor
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private var id: Long,
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
    constructor() : this(-1, "", "", 1, byteArrayOf())

    constructor(firstName: String, lastName: String, image: ByteArray) : this(
        -1,
        firstName,
        lastName,
        1,
        image
    )

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

    fun setImage(image: ByteArray) {
        this.image = image
    }
}