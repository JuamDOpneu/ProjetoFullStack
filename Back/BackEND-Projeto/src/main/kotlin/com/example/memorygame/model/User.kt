package com.example.memorygame.model

import jakarta.persistence.*

// @Entity: Transforma essa classe numa tabela do Banco de Dados.
// @Table: O nome da tabela será "users".
@Entity
@Table(name = "users")
data class User(
    // Define o ID (Identidade) do usuário.
    // O banco de dados vai gerar esse número sozinho (1, 2, 3...) automaticamente.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    // Nome que vai aparecer na tela.
    val name: String = "",

    // @Column(unique = true): Regra importante!
    // O banco de dados NÃO vai deixar cadastrar dois usuários com o mesmo email.
    @Column(unique = true)
    val email: String = "",

    // Aqui guardamos a senha.
    // IMPORTANTE: Não guardamos "123456", guardamos um código criptografado gigante.
    val password: String = "",

    // Link da foto de perfil.
    // O "?" e "= null" significam que o usuário pode começar SEM foto (opcional).
    var avatarUrl: String? = null,

    // Contadores para as estatísticas (começam com 0).
    var gamesPlayed: Int = 0,

    var wins: Int = 0
)