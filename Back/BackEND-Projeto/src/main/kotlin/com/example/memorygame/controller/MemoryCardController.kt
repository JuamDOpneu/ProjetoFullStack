package com.example.memorygame.controller

import com.example.memorygame.model.MemoryCard
import com.example.memorygame.repository.MemoryCardRepository
import com.example.memorygame.service.FileStorageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/cards")
@CrossOrigin(origins = ["*"])
class MemoryCardController(
    private val repository: MemoryCardRepository,
    private val fileStorageService: FileStorageService
) {

    @GetMapping
    fun getAllCards(@RequestParam(required = false) theme: String?): ResponseEntity<List<MemoryCard>> {
        val cards = if (theme.isNullOrBlank()) {

            repository.findAll()
        } else {

            repository.findAll().filter { it.theme.equals(theme, ignoreCase = true) }
        }
        return ResponseEntity.ok(cards)
    }


    @GetMapping("/{id}")
    fun getCardById(@PathVariable id: Long): ResponseEntity<MemoryCard> {
        return repository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }


    @GetMapping("/themes")
    fun getDistinctThemes(): ResponseEntity<List<String>> {
        val themes = repository.findAll()
            .map { it.theme }
            .distinct()
            .sorted()
        return ResponseEntity.ok(themes)
    }


    @PostMapping(consumes = ["multipart/form-data"])
    fun createCard(
        @RequestParam("name") name: String,
        @RequestParam("theme") theme: String,
        @RequestParam("image") image: MultipartFile
    ): ResponseEntity<MemoryCard> {

        val imagePath = fileStorageService.storeFile(image)

        val fullUrl = "http://localhost:8080$imagePath"

        val newCard = MemoryCard(
            name = name,
            theme = theme,
            imageUrl = fullUrl
        )

        return ResponseEntity.ok(repository.save(newCard))
    }


    @PutMapping("/{id}", consumes = ["multipart/form-data"])
    fun updateCard(
        @PathVariable id: Long,
        @RequestParam("name") name: String,
        @RequestParam("theme") theme: String,
        @RequestParam(value = "image", required = false) image: MultipartFile? // Pode vir nulo
    ): ResponseEntity<MemoryCard> {


        val existingCard = repository.findById(id).orElseThrow { RuntimeException("Carta não encontrada") }


        var newImageUrl = existingCard.imageUrl


        if (image != null && !image.isEmpty) {

            val imagePath = fileStorageService.storeFile(image)
            newImageUrl = "http://localhost:8080$imagePath"
        }


        val updatedCard = existingCard.copy(
            name = name,
            theme = theme,
            imageUrl = newImageUrl
        )
        updatedCard.id = existingCard.id

        return ResponseEntity.ok(repository.save(updatedCard))
    }

    // 6. DELETAR Carta
    @DeleteMapping("/{id}")
    fun deleteCard(@PathVariable id: Long): ResponseEntity<Void> {
        if (repository.existsById(id)) {
            repository.deleteById(id)
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.notFound().build()
    }
}