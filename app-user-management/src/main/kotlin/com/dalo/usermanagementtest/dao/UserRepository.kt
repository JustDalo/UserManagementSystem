package com.dalo.usermanagementtest.dao

import com.dalo.usermanagementtest.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {

}