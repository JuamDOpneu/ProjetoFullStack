package com.example.memorygame

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MemoryGameApplication

// fun main: Todo programa Kotlin começa por aqui. É a porta de entrada.
fun main(args: Array<String>) {


    runApplication<MemoryGameApplication>(*args)
}