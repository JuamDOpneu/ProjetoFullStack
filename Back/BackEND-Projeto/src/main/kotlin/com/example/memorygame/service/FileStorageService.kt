package com.example.memorygame.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.UUID


@Service
class FileStorageService {


    private val uploadDir = Paths.get("uploads")


    init {

        if (!Files.exists(uploadDir)) {

            Files.createDirectory(uploadDir)
        }
    }


    fun storeFile(file: MultipartFile): String {


        val fileName = UUID.randomUUID().toString() + "_" + file.originalFilename


        val targetLocation = uploadDir.resolve(fileName)


        Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)


        return "/images/$fileName"
    }
}