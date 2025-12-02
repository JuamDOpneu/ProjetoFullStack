package com.example.memorygame.model

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime


@MappedSuperclass
abstract class BaseEntity(


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,


    open val createdAt: LocalDateTime = LocalDateTime.now()
)