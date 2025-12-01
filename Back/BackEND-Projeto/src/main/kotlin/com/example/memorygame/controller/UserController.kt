package com.example.memorygame.controller

import com.example.memorygame.model.User
import com.example.memorygame.repository.UserRepository
import com.example.memorygame.service.FileStorageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

// Classe simples (DTO) para receber os dados quando o jogo acaba.
// O Front manda: "Ganhou? Sim/Não", "Quantos movimentos?", "Quanto tempo?".
data class GameResultRequest(val win: Boolean, val moves: Int, val time: Int)

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userRepository: UserRepository,       // Para salvar/buscar dados do usuário no banco
    private val fileStorageService: FileStorageService // Para salvar a foto do avatar na pasta
) {

    // 1. BUSCAR ESTATÍSTICAS
    // Usado na tela de Perfil para mostrar "Jogos Jogados: 10 | Vitórias: 5"
    @GetMapping("/{id}/stats")
    fun getStats(@PathVariable id: Long): ResponseEntity<Map<String, Int>> {
        // Busca o usuário pelo ID
        val user = userRepository.findById(id).orElseThrow()

        // Retorna apenas os números importantes
        return ResponseEntity.ok(mapOf(
            "gamesPlayed" to user.gamesPlayed,
            "wins" to user.wins
        ))
    }

    // 2. SALVAR RESULTADO DO JOGO
    // O Front chama essa rota assim que o jogador termina uma partida.
    @PostMapping("/{id}/game-result")
    fun saveGameResult(@PathVariable id: Long, @RequestBody result: GameResultRequest): ResponseEntity<Any> {
        val user = userRepository.findById(id).orElseThrow()

        // Sempre aumenta o número de partidas jogadas
        user.gamesPlayed += 1

        // Se o resultado foi vitória, aumenta o contador de vitórias
        if (result.win) {
            user.wins += 1
        }

        // Salva os novos números no banco
        userRepository.save(user)

        return ResponseEntity.ok().build()
    }

    // 3. UPLOAD DE AVATAR (FOTO DE PERFIL)
    @PutMapping("/{id}/avatar")
    fun uploadAvatar(
        @PathVariable id: Long,
        @RequestParam("avatar") file: MultipartFile // Recebe o arquivo da imagem
    ): ResponseEntity<User> {

        // Passo A: Salva o arquivo fisicamente na pasta 'uploads' do computador
        // (Reutilizamos o mesmo serviço que cria as cartas)
        val imagePath = fileStorageService.storeFile(file)

        // Passo B: Cria o link para acessar essa imagem
        val fullUrl = "http://localhost:8080$imagePath"

        // Passo C: Atualiza o perfil do usuário no banco com o novo link
        val user = userRepository.findById(id).orElseThrow()
        user.avatarUrl = fullUrl
        userRepository.save(user)

        // Retorna o usuário atualizado para o Front já mostrar a foto nova
        return ResponseEntity.ok(user)
    }
}