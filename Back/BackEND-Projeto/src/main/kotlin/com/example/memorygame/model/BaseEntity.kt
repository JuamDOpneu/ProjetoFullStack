package com.example.memorygame.model

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

// @MappedSuperclass: Diz ao banco de dados: "Não crie uma tabela para essa classe específica,
// mas pegue os campos dela (id, createdAt) e adicione nas tabelas das classes filhas (User, MemoryCard)".
@MappedSuperclass
abstract class BaseEntity(

    // @Id: Define que este campo é a Chave Primária.
    // @GeneratedValue: Configura para o banco gerar o número sozinho (1, 2, 3...) - Auto Incremento.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,

    // Captura automaticamente a data e hora do sistema no momento que o objeto é criado.
    // É útil para saber "quando esse usuário se cadastrou?" ou "quando essa carta foi criada?".
    open val createdAt: LocalDateTime = LocalDateTime.now()
)