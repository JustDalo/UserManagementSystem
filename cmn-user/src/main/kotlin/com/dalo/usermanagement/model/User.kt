package com.dalo.usermanagement.model

import lombok.Data
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.GenerationType
import javax.persistence.GeneratedValue
import javax.persistence.SequenceGenerator
import javax.validation.constraints.NotBlank

@Data
@Entity
@Table(name = "users", schema = "users")
class User(
    @Id
    @Column(name = "usr_id")
    @SequenceGenerator(name = "pk_sequence", sequenceName = "entity_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    private var id: Long,
    @NotBlank
    @Column(name = "usr_first_name")
    private var firstName: String,
    @NotBlank
    @Column(name = "usr_last_name")
    private var lastName: String
) {
    constructor() : this(-1, "", "")

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
}