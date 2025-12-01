package com.example.memorygame.repository

import com.example.memorygame.model.MemoryCard
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

// @Repository: Diz ao Spring que isso aqui vai acessar o banco de dados.
@Repository
// JpaRepository: É uma "caixa de ferramentas" pronta.
// Só de herdar dela, já ganhamos métodos prontos para Salvar, Deletar e Buscar por ID, sem digitar nada.
interface MemoryCardRepository : JpaRepository<MemoryCard, Long> {

    // --- MÉTODOS MÁGICOS (O Spring cria o SQL sozinho pelo nome) ---

    // Busca cartas pelo tema. O "IgnoreCase" serve para ignorar letras maiúsculas/minúsculas.
    // Ex: Se buscar "herois", ele acha "Herois", "HEROIS", etc.
    fun findByThemeIgnoreCase(theme: String): List<MemoryCard>

    // Busca cartas pesquisando por parte do nome (igual a busca do Google).
    // Ex: Se digitar "super", ele acha "Superman".
    fun findByNameContainingIgnoreCase(name: String): List<MemoryCard>

    // --- CONSULTA PERSONALIZADA (Aqui a gente escreve um pouco de SQL/JPQL) ---

    // Essa função serve para o filtro de temas na tela principal.
    // O "SELECT DISTINCT" diz ao banco: "Me traga a lista de temas, mas sem repetir nomes iguais".
    @Query("SELECT DISTINCT m.theme FROM MemoryCard m")
    fun findDistinctThemes(): List<String>
}