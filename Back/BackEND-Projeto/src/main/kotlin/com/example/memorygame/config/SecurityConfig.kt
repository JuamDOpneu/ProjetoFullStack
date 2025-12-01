package com.example.memorygame.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig {

    // Define que as senhas serão criptografadas antes de salvar no banco.
    // Assim, se alguém ver o banco, não descobre a senha real do usuário.
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    // Configura toda a "portaria" de segurança do sistema.
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            // Ativa o CORS (configurado lá embaixo) para o Front-end conseguir acessar aqui.
            .cors { it.configurationSource(corsConfigurationSource()) }

            // Desabilita proteções que não precisamos agora (facilita os testes).
            .csrf { it.disable() }

            // Define que o servidor não vai guardar "sessão" do usuário.
            // O usuário terá que se identificar a cada requisição (usando Token depois).
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

            // Aqui definimos quem pode acessar o quê:
            .authorizeHttpRequests { auth ->
                auth
                    // Qualquer um pode tentar fazer Login ou Cadastro (POST).
                    .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()

                    // Qualquer um pode ver as imagens do jogo.
                    .requestMatchers(HttpMethod.GET, "/images/**").permitAll()

                    // Libera páginas de erro do sistema.
                    .requestMatchers("/error").permitAll()

                    // Todo o resto está liberado por enquanto (para facilitar seus testes).
                    // (Obs: Num sistema real, aqui bloquearíamos tudo que não fosse login).
                    .anyRequest().permitAll()
            }

        return http.build()
    }

    // Configuração específica para o React conversar com o Java.
    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val configuration = CorsConfiguration()

        // Autoriza apenas o nosso Front-end (React) que roda na porta 5173.
        configuration.allowedOrigins = listOf("http://localhost:5173")

        // Libera os métodos comuns (GET para buscar, POST para salvar, etc).
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")

        // Permite enviar cabeçalhos (como o Token de autorização).
        configuration.allowedHeaders = listOf("*")

        // Permite credenciais (necessário para logins seguros).
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}