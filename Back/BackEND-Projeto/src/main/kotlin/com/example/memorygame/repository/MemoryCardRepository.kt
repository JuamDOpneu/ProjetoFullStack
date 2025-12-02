package com.example.memorygame.repository

import com.example.memorygame.model.MemoryCard
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository

interface MemoryCardRepository : JpaRepository<MemoryCard, Long> {



    fun findByThemeIgnoreCase(theme: String): List<MemoryCard>

    fun findByNameContainingIgnoreCase(name: String): List<MemoryCard>



    @Query("SELECT DISTINCT m.theme FROM MemoryCard m")
    fun findDistinctThemes(): List<String>
}