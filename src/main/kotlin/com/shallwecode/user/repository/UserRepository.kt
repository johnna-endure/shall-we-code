package com.shallwecode.user.repository

import com.shallwecode.user.entity.UserTable
import com.shallwecode.user.entity.embeddable.Email
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserTable, Long> {
    fun findByEmail(email: Email): UserTable?

}