package com.dalo.usermanagementsystem.model

import lombok.Data
import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.Table


@Data
@Table(name = "user")
class User(
    @Id
    @Column(name="usr_id")
    var id: Long,
    @Column(name="urs_first_name")
    var firstName: String,
    @Column(name="usr_last_name")
    var lastName: String
)