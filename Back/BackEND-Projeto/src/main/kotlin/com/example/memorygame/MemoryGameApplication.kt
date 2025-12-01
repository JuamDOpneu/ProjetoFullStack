package com.example.memorygame

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// @SpringBootApplication: Essa é a anotação principal.
// Ela avisa o sistema: "Isso é um projeto Spring Boot. Faça a configuração automática."
// Ela também manda o Spring procurar (scan) por todos os seus Controllers, Services e Repositories.
@SpringBootApplication
class MemoryGameApplication

// fun main: Todo programa Kotlin começa por aqui. É a porta de entrada.
fun main(args: Array<String>) {

    // runApplication: Esse é o comando que realmente "liga" o servidor.
    // Ele inicia o Tomcat (servidor web) na porta 8080 e deixa o site pronto para receber visitas.
    runApplication<MemoryGameApplication>(*args)
}