package com.example.memorygame.service

import com.example.memorygame.model.MemoryCard

// Interface = Um Contrato ou Menu de Opções.
// Aqui nós listamos as regras do jogo: "O sistema TEM QUE ter essas funções abaixo".
// Quem for implementar esse serviço (a classe CardServiceImpl) é obrigado a criar esses códigos.
// *Isso atende ao requisito de Polimorfismo/Abstração do seu trabalho.*
interface CardService {

    // Lista todas as cartas que existem.
    fun findAll(): List<MemoryCard>

    // Tenta achar uma carta pelo ID (o '?' diz que pode não achar nada).
    fun findById(id: Long): MemoryCard?

    // Busca cartas filtrando por nome ou tema.
    fun search(name: String?, theme: String?): List<MemoryCard>

    // Salva uma carta nova.
    fun save(card: MemoryCard): MemoryCard

    // Atualiza uma carta que já existe.
    fun update(id: Long, card: MemoryCard): MemoryCard?

    // Apaga uma carta. Retorna 'true' se conseguiu apagar.
    fun delete(id: Long): Boolean

    // Busca apenas os nomes dos temas (para preencher o menu de filtros).
    fun findDistinctThemes(): List<String>
}