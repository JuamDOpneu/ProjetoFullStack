package com.example.memorygame.repository

import com.example.memorygame.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional


interface UserRepository : JpaRepository<User, Long> {


    fun findByEmail(email: String): Optional<User>
}