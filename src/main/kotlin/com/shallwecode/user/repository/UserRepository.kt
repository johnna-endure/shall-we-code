package com.shallwecode.user.repository

import com.shallwecode.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long?> {
    fun findUserByName(name: String) : User?
}