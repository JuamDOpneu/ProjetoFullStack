package com.example.memorygame.service

import com.example.memorygame.model.MemoryCard
import com.example.memorygame.repository.MemoryCardRepository
import org.springframework.stereotype.Service

// @Service: Avisa ao Spring que essa classe contém a "Lógica de Negócio".
// É aqui que tomamos as decisões antes de gravar no banco.
@Service
class DatabaseCardService(
    private val repository: MemoryCardRepository // Precisamos do repositório para acessar o banco.
) : CardService { // Aqui dizemos: "Eu sigo as regras da interface CardService".

    // Apenas repassa o pedido para o repositório buscar tudo.
    override fun findAll(): List<MemoryCard> = repository.findAll()

    // Busca por ID. O .orElse(null) serve para não quebrar o código se o ID não existir.
    override fun findById(id: Long): MemoryCard? = repository.findById(id).orElse(null)

    // --- CÉREBRO DA BUSCA ---
    // Aqui decidimos como filtrar as cartas.
    override fun search(name: String?, theme: String?): List<MemoryCard> {
        return when {
            // 1. Se o usuário digitou um nome, buscamos por nome (ex: "Go" acha "Goku").
            !name.isNullOrBlank() -> repository.findByNameContainingIgnoreCase(name)

            // 2. Se o usuário escolheu um tema, buscamos pelo tema exato.
            !theme.isNullOrBlank() -> repository.findByThemeIgnoreCase(theme)

            // 3. Se ele não digitou nada, retornamos a lista completa.
            else -> repository.findAll()
        }
    }

    // Salva uma carta nova no banco.
    override fun save(card: MemoryCard): MemoryCard = repository.save(card)

    // Atualiza uma carta existente.
    override fun update(id: Long, card: MemoryCard): MemoryCard? {
        // Primeiro: Verificamos se a carta existe mesmo.
        if (!repository.existsById(id)) {
            return null
        }

        // Criamos um objeto novo com os dados que vieram da tela...
        val cardToUpdate = MemoryCard(
            name = card.name,
            theme = card.theme,
            imageUrl = card.imageUrl
        ).apply {
            // ...MAS forçamos o ID a ser o mesmo da carta antiga.
            this.id = id
        }

        // Quando usamos o .save() com um ID que já existe no banco,
        // o Spring entende que é para ATUALIZAR, e não criar uma nova.
        return repository.save(cardToUpdate)
    }

    // Deleta a carta.
    override fun delete(id: Long): Boolean {
        return if (repository.existsById(id)) {
            repository.deleteById(id)
            true // Retorna verdadeiro (deletou com sucesso)
        } else {
            false // Retorna falso (não achou a carta para deletar)
        }
    }

    // Busca a lista de temas únicos para preencher o filtro do site.
    override fun findDistinctThemes(): List<String> {
        return repository.findDistinctThemes()
    }
}