package com.dalo.usermanagementsystem.model

import lombok.Data
import lombok.Getter
import lombok.Setter
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Data
@Entity
@Table(name = "users", schema = "users")
@Getter
@Setter
class User(
    @Id
    @Column(name="usr_id")
    private var id: Long,
    @Column(name="usr_first_name")
    private var firstName: String,
    @Column(name="usr_last_name")
    private var lastName: String
) {
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