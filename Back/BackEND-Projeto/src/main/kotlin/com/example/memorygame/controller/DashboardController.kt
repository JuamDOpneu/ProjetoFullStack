package com.example.memorygame.controller

import com.example.memorygame.repository.MemoryCardRepository
import com.example.memorygame.repository.UserRepository
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


data class DashboardStats(
    val totalCards: Long,
    val totalPlayers: Long,
    val availableThemes: List<String>
)

@RestController
@RequestMapping("/api/dashboard")

@CrossOrigin(origins = ["*"])
class DashboardController(

    private val cardRepository: MemoryCardRepository,
    private val playerRepository: UserRepository
) {


    @GetMapping("/stats")
    fun getStats(): DashboardStats {


        val allCards = cardRepository.findAll()


        val allPlayers = playerRepository.count()


        val themes = allCards.map { it.theme }.distinct()

        return DashboardStats(
            totalCards = allCards.size.toLong(),
            totalPlayers = allPlayers,
            availableThemes = themes
        )
    }
}