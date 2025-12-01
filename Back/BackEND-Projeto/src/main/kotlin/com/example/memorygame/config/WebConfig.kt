package com.example.memorygame.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    // Esse método ensina o servidor a encontrar as imagens que foram salvas.
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {

        // 1. O Gatilho: Quando o navegador pedir algo começando com "/images/"...
        registry.addResourceHandler("/images/**")

            // 2. O Destino: ...o sistema busca o arquivo na pasta "uploads" do computador.
            // O "file:" é importante para dizer que é uma pasta física no disco.
            .addResourceLocations("file:uploads/")
    }
}