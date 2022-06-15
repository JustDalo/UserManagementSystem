package com.dalo.usermanagementtest.model

import lombok.Getter
import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class UserRole(roleId: Long) {
    ROLE_USER(1L),
    ROLE_ADMIN(2L);

    @Getter
    private val roleNumber: Long = roleId

}