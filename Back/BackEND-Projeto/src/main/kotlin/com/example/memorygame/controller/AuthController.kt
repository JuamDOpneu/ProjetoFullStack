package com.example.memorygame.controller

import com.example.memorygame.model.User
import com.example.memorygame.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*

// === CLASSES AUXILIARES (DTOs) ===
// Servem apenas para transportar dados do Front para o Back e vice-versa.

// Recebe os dados do formulário de cadastro.
data class RegisterRequest(val name: String, val email: String, val password: String)

// Recebe os dados do formulário de login.
data class LoginRequest(val email: String, val password: String)

// IMPORTANTE: É a resposta que enviamos pro Front-end.
// O Front precisa do 'user' para mostrar o nome no perfil e do 'token' para manter o login.
data class AuthResponse(val token: String, val user: User)

@RestController
@RequestMapping("/api/auth") // Define que o endereço base é http://localhost:8080/api/auth
class AuthController(
    // Injeção de dependências: O Spring nos dá essas ferramentas prontas.
    private val userRepository: UserRepository,   // Para falar com o banco de dados
    private val passwordEncoder: PasswordEncoder  // Para criptografar senhas
) {

    // --- ROTA DE CADASTRO ---
    @PostMapping("/register")
    fun register(@RequestBody req: RegisterRequest): ResponseEntity<Any> {

        // 1. Verificação: Se o email já existe, paramos aqui.
        if (userRepository.findByEmail(req.email).isPresent) {
            return ResponseEntity.badRequest().body("Email já cadastrado")
        }

        // 2. Criação: Montamos o usuário novo.
        val newUser = User(
            name = req.name,
            email = req.email,
            // A senha é criptografada antes de ser salva (segurança básica).
            password = passwordEncoder.encode(req.password)
        )

        // 3. Persistência: Salvamos no banco.
        userRepository.save(newUser)

        return ResponseEntity.ok("Usuário criado com sucesso")
    }

    // --- ROTA DE LOGIN ---
    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<Any> {

        // 1. Busca: Tentamos achar o usuário pelo email.
        val user = userRepository.findByEmail(req.email)
            .orElse(null) ?: return ResponseEntity.badRequest().body("Usuário não encontrado")

        // 2. Senha: O encoder compara a senha digitada (req.password) com a criptografada no banco (user.password).
        if (!passwordEncoder.matches(req.password, user.password)) {
            return ResponseEntity.badRequest().body("Senha incorreta")
        }

        // 3. Token: Geramos um código único (simulando um Token real) para identificar essa sessão.
        val fakeToken = UUID.randomUUID().toString()

        // 4. Resposta: Devolvemos o Token e os dados do Usuário para o Front-end usar.
        return ResponseEntity.ok(AuthResponse(fakeToken, user))
    }
}