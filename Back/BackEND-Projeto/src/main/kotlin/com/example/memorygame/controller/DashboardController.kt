package com.example.memorygame.controller

import com.example.memorygame.repository.MemoryCardRepository
import com.example.memorygame.repository.UserRepository
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// Essa classe (DTO) é apenas um pacote de dados.
// Ela define exatamente o que o Frontend vai receber: total de cartas, jogadores e lista de temas.
data class DashboardStats(
    val totalCards: Long,
    val totalPlayers: Long,
    val availableThemes: List<String>
)

@RestController
@RequestMapping("/api/dashboard")
// @CrossOrigin: Permite que o Frontend chame essa API sem ser bloqueado.
@CrossOrigin(origins = ["*"])
class DashboardController(
    // Precisamos acessar o banco de dados das Cartas e dos Usuários.
    private val cardRepository: MemoryCardRepository,
    private val playerRepository: UserRepository
) {

    // Quando alguém acessar "/api/dashboard/stats"...
    @GetMapping("/stats")
    fun getStats(): DashboardStats {

        // 1. Buscamos todas as cartas que existem no banco.
        val allCards = cardRepository.findAll()

        // 2. Contamos quantos jogadores estão cadastrados.
        val allPlayers = playerRepository.count()

        // 3. Lógica dos Temas:
        // Pegamos a lista de cartas, extraímos só o nome do tema de cada uma,
        // e usamos o .distinct() para remover duplicatas (ex: não mostrar "Herois" 10 vezes).
        val themes = allCards.map { it.theme }.distinct()

        // 4. Retornamos o pacote pronto com os números calculados.
        return DashboardStats(
            totalCards = allCards.size.toLong(),
            totalPlayers = allPlayers,
            availableThemes = themes
        )
    }
}