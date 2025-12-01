package com.example.memorygame.controller

import com.example.memorygame.model.MemoryCard
import com.example.memorygame.repository.MemoryCardRepository
import com.example.memorygame.service.FileStorageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/cards") // O endereço principal é .../api/cards
@CrossOrigin(origins = ["*"]) // Libera o React para acessar
class MemoryCardController(
    private val repository: MemoryCardRepository,      // Ferramenta para falar com o banco de dados
    private val fileStorageService: FileStorageService // Ferramenta para salvar arquivos na pasta do PC
) {

    // 1. Listar Cartas
    // Se o front mandar um tema (ex: ?theme=Herois), filtramos. Se não, devolvemos tudo.
    @GetMapping
    fun getAllCards(@RequestParam(required = false) theme: String?): ResponseEntity<List<MemoryCard>> {
        val cards = if (theme.isNullOrBlank()) {
            // Busca todas as cartas do banco
            repository.findAll()
        } else {
            // Busca todas e filtra apenas as que têm o tema igual ao pedido
            repository.findAll().filter { it.theme.equals(theme, ignoreCase = true) }
        }
        return ResponseEntity.ok(cards)
    }

    // 2. Pegar Carta Única
    // Usado quando clicamos em "Editar" no site, para preencher os campos com os dados atuais.
    @GetMapping("/{id}")
    fun getCardById(@PathVariable id: Long): ResponseEntity<MemoryCard> {
        return repository.findById(id)
            .map { ResponseEntity.ok(it) } // Se achou, retorna a carta
            .orElse(ResponseEntity.notFound().build()) // Se não achou, retorna erro 404
    }

    // 3. Listar Temas
    // Serve para preencher aquela caixinha de seleção (Dropdown) no site.
    @GetMapping("/themes")
    fun getDistinctThemes(): ResponseEntity<List<String>> {
        val themes = repository.findAll()
            .map { it.theme } // Pega só os nomes dos temas
            .distinct()       // Remove os repetidos (ex: não mostrar "Herois" 2 vezes)
            .sorted()         // Coloca em ordem alfabética
        return ResponseEntity.ok(themes)
    }

    // 4. CRIAR Carta
    // Recebe o arquivo da imagem e os textos via formulário.
    @PostMapping(consumes = ["multipart/form-data"])
    fun createCard(
        @RequestParam("name") name: String,
        @RequestParam("theme") theme: String,
        @RequestParam("image") image: MultipartFile // Aqui a imagem é OBRIGATÓRIA
    ): ResponseEntity<MemoryCard> {

        // Usa o nosso serviço para salvar o arquivo físico na pasta 'uploads'
        val imagePath = fileStorageService.storeFile(image)

        // Cria o endereço completo para acessar essa imagem depois (ex: http://localhost:8080/images/foto.png)
        val fullUrl = "http://localhost:8080$imagePath"

        // Monta o objeto para salvar no banco
        val newCard = MemoryCard(
            name = name,
            theme = theme,
            imageUrl = fullUrl
        )

        // Salva e retorna
        return ResponseEntity.ok(repository.save(newCard))
    }

    // 5. EDITAR Carta
    // Aqui a lógica é um pouco mais esperta porque a imagem é OPCIONAL.
    @PutMapping("/{id}", consumes = ["multipart/form-data"])
    fun updateCard(
        @PathVariable id: Long,
        @RequestParam("name") name: String,
        @RequestParam("theme") theme: String,
        @RequestParam(value = "image", required = false) image: MultipartFile? // Pode vir nulo
    ): ResponseEntity<MemoryCard> {

        // Busca a carta antiga no banco
        val existingCard = repository.findById(id).orElseThrow { RuntimeException("Carta não encontrada") }

        // Começamos assumindo que a imagem vai continuar a mesma que já estava
        var newImageUrl = existingCard.imageUrl

        // SE o usuário enviou uma nova imagem...
        if (image != null && !image.isEmpty) {
            // ...a gente salva o novo arquivo e atualiza o link
            val imagePath = fileStorageService.storeFile(image)
            newImageUrl = "http://localhost:8080$imagePath"
        }

        // Atualiza os dados da carta mantendo o mesmo ID
        val updatedCard = existingCard.copy(
            name = name,
            theme = theme,
            imageUrl = newImageUrl
        )
        updatedCard.id = existingCard.id // Garante que é uma atualização, não criação

        return ResponseEntity.ok(repository.save(updatedCard))
    }

    // 6. DELETAR Carta
    @DeleteMapping("/{id}")
    fun deleteCard(@PathVariable id: Long): ResponseEntity<Void> {
        if (repository.existsById(id)) {
            repository.deleteById(id) // Apaga do banco
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.notFound().build()
    }
}