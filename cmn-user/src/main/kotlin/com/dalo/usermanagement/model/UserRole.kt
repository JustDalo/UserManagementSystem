package com.dalo.usermanagement.model

import lombok.Getter

enum class UserRole(private val roleId: Long) {
    USER(1L),
    ADMIN(2L);

    @Getter
    private val roleNumber: Long = roleId;


}