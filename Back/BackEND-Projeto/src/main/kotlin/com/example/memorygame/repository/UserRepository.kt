package com.example.memorygame.repository

import com.example.memorygame.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

// Interface que conecta o código com a tabela de Usuários no banco de dados.
// Ao herdar de JpaRepository, já ganhamos funções de salvar e buscar por ID de graça.
interface UserRepository : JpaRepository<User, Long> {

    // Método mágico criado para o Login.
    // Nós só declaramos o nome, e o Spring cria o SQL sozinho: "SELECT * FROM users WHERE email = ..."
    //
    // Retorna "Optional" porque pode ser que o email não exista no banco (usuário não cadastrado).
    fun findByEmail(email: String): Optional<User>
}