package com.dalo.model

import lombok.Data
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.GenerationType
import javax.persistence.GeneratedValue

@Data
@Entity
@Table(name = "users", schema = "users")
class User(
    @Id
    @Column(name = "usr_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long,
    @Column(name = "usr_first_name")
    private var firstName: String,
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