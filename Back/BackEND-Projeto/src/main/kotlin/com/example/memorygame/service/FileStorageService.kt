package com.example.memorygame.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.UUID

// @Service: Indica que essa classe contém lógica de negócio.
@Service
class FileStorageService {

    // Define o nome da pasta onde vamos salvar as fotos.
    // Paths.get cria um caminho que funciona tanto em Windows quanto em Linux/Mac.
    private val uploadDir = Paths.get("uploads")

    // O bloco 'init' roda automaticamente assim que o sistema inicia.
    init {
        // Verifica: "A pasta 'uploads' existe?"
        if (!Files.exists(uploadDir)) {
            // Se não existir, ele cria a pasta agora. Isso evita erros de "Diretório não encontrado".
            Files.createDirectory(uploadDir)
        }
    }

    // Função principal: Recebe o arquivo (MultipartFile) vindo do formulário e salva no disco.
    fun storeFile(file: MultipartFile): String {

        // 1. Gerar Nome Único:
        // Se dois usuários subirem uma foto chamada "avatar.png", uma apagaria a outra.
        // O UUID gera um código aleatório gigante (ex: "a1b2-c3d4...") para garantir que cada arquivo tenha um nome exclusivo.
        val fileName = UUID.randomUUID().toString() + "_" + file.originalFilename

        // 2. Definir Destino:
        // Junta a pasta ("uploads") com o nome do arquivo ("a1b2..._foto.png").
        val targetLocation = uploadDir.resolve(fileName)

        // 3. Salvar:
        // Pega os dados do arquivo (inputStream) e copia para o destino final.
        // O REPLACE_EXISTING diz que se, por um milagre, existir um arquivo igual, pode substituir.
        Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)

        // 4. Retorno:
        // Devolvemos apenas o caminho da URL (ex: "/images/arquivo.png") para salvar no Banco de Dados.
        // O navegador vai usar esse caminho depois para exibir a imagem.
        return "/images/$fileName"
    }
}