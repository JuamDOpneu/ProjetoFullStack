package com.example.memorygame.service

import com.example.memorygame.model.MemoryCard
import com.example.memorygame.repository.MemoryCardRepository
import org.springframework.stereotype.Service


@Service
class DatabaseCardService(
    private val repository: MemoryCardRepository
) : CardService {


    override fun findAll(): List<MemoryCard> = repository.findAll()


    override fun findById(id: Long): MemoryCard? = repository.findById(id).orElse(null)


    override fun search(name: String?, theme: String?): List<MemoryCard> {
        return when {

            !name.isNullOrBlank() -> repository.findByNameContainingIgnoreCase(name)

            !theme.isNullOrBlank() -> repository.findByThemeIgnoreCase(theme)


            else -> repository.findAll()
        }
    }


    override fun save(card: MemoryCard): MemoryCard = repository.save(card)


    override fun update(id: Long, card: MemoryCard): MemoryCard? {

        if (!repository.existsById(id)) {
            return null
        }


        val cardToUpdate = MemoryCard(
            name = card.name,
            theme = card.theme,
            imageUrl = card.imageUrl
        ).apply {

            this.id = id
        }


        return repository.save(cardToUpdate)
    }

    // Deleta a carta.
    override fun delete(id: Long): Boolean {
        return if (repository.existsById(id)) {
            repository.deleteById(id)
            true
        } else {
            false
        }
    }


    override fun findDistinctThemes(): List<String> {
        return repository.findDistinctThemes()
    }
}