package com.example.memorygame.controller

import com.example.memorygame.model.User
import com.example.memorygame.repository.UserRepository
import com.example.memorygame.service.FileStorageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


data class GameResultRequest(val win: Boolean, val moves: Int, val time: Int)

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userRepository: UserRepository,
    private val fileStorageService: FileStorageService
) {

    @GetMapping("/{id}/stats")
    fun getStats(@PathVariable id: Long): ResponseEntity<Map<String, Int>> {

        val user = userRepository.findById(id).orElseThrow()

        return ResponseEntity.ok(mapOf(
            "gamesPlayed" to user.gamesPlayed,
            "wins" to user.wins
        ))
    }


    @PostMapping("/{id}/game-result")
    fun saveGameResult(@PathVariable id: Long, @RequestBody result: GameResultRequest): ResponseEntity<Any> {
        val user = userRepository.findById(id).orElseThrow()


        user.gamesPlayed += 1


        if (result.win) {
            user.wins += 1
        }


        userRepository.save(user)

        return ResponseEntity.ok().build()
    }


    @PutMapping("/{id}/avatar")
    fun uploadAvatar(
        @PathVariable id: Long,
        @RequestParam("avatar") file: MultipartFile
    ): ResponseEntity<User> {

        val imagePath = fileStorageService.storeFile(file)

        val fullUrl = "http://localhost:8080$imagePath"

        val user = userRepository.findById(id).orElseThrow()
        user.avatarUrl = fullUrl
        userRepository.save(user)

        return ResponseEntity.ok(user)
    }
}