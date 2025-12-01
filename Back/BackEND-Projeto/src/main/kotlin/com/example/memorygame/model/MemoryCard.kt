package com.example.memorygame.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Lob
import jakarta.persistence.Table

// @Entity: Avisa ao Spring que essa classe representa uma tabela no Banco de Dados.
@Entity
// @Table: Define que o nome da tabela lá no banco será "memory_cards".
@Table(name = "memory_cards")
data class MemoryCard(
    val name: String,   // Nome da carta (ex: "Goku", "Pikachu")
    val theme: String,  // Categoria da carta (ex: "Animes", "Animais")

    // @Column(... TEXT): URLs de imagens podem ser muito longas.
    // Esse comando garante que o banco reserve espaço suficiente para o texto não ser cortado.
    @Column(columnDefinition = "TEXT")
    val imageUrl: String // Aqui salvamos apenas o LINK da imagem (ex: http://localhost.../foto.png)

) : BaseEntity() // Herda o ID e a Data de Criação automaticamente da classe BaseEntity.