package com.dalo.usermanagementsystem.dao

import com.dalo.usermanagementsystem.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {

}