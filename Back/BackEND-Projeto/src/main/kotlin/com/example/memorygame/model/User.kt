package com.example.memorygame.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,


    val name: String = "",


    @Column(unique = true)
    val email: String = "",


    val password: String = "",


    var avatarUrl: String? = null,


    var gamesPlayed: Int = 0,

    var wins: Int = 0
)