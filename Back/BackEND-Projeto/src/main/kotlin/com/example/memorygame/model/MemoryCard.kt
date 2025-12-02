package com.example.memorygame.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Lob
import jakarta.persistence.Table


@Entity

@Table(name = "memory_cards")
data class MemoryCard(
    val name: String,
    val theme: String,


    @Column(columnDefinition = "TEXT")
    val imageUrl: String

) : BaseEntity()