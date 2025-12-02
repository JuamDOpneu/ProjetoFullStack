package com.example.memorygame.service

import com.example.memorygame.model.MemoryCard


interface CardService {


    fun findAll(): List<MemoryCard>


    fun findById(id: Long): MemoryCard?


    fun search(name: String?, theme: String?): List<MemoryCard>

    fun save(card: MemoryCard): MemoryCard


    fun update(id: Long, card: MemoryCard): MemoryCard?

    fun delete(id: Long): Boolean


    fun findDistinctThemes(): List<String>
}