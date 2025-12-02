package com.example.memorygame.controller

import com.example.memorygame.model.User
import com.example.memorygame.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*


data class RegisterRequest(val name: String, val email: String, val password: String)

// Recebe os dados do formulário de login.
data class LoginRequest(val email: String, val password: String)


data class AuthResponse(val token: String, val user: User)

@RestController
@RequestMapping("/api/auth")
class AuthController(

    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/register")
    fun register(@RequestBody req: RegisterRequest): ResponseEntity<Any> {


        if (userRepository.findByEmail(req.email).isPresent) {
            return ResponseEntity.badRequest().body("Email já cadastrado")
        }

        val newUser = User(
            name = req.name,
            email = req.email,

            password = passwordEncoder.encode(req.password)
        )

        userRepository.save(newUser)

        return ResponseEntity.ok("Usuário criado com sucesso")
    }

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<Any> {


        val user = userRepository.findByEmail(req.email)
            .orElse(null) ?: return ResponseEntity.badRequest().body("Usuário não encontrado")


        if (!passwordEncoder.matches(req.password, user.password)) {
            return ResponseEntity.badRequest().body("Senha incorreta")
        }


        val fakeToken = UUID.randomUUID().toString()


        return ResponseEntity.ok(AuthResponse(fakeToken, user))
    }
}