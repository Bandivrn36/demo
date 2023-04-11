package com.example.demo.service


import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service



@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: BCryptPasswordEncoder) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw UsernameNotFoundException("Username is null")
        }
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User not found")
        return CustomUserDetails(user)
    }

    fun createUser(user: User): User? {
        val existingUser = userRepository.findByUsername(user.username)
        if (existingUser != null) {
            return null
        }
        user.password = passwordEncoder.encode(user.password)
        return userRepository.save(user)
    }

    fun getCurrentUser(): User? {
        val user = SecurityContextHolder.getContext().authentication.principal as CustomUserDetails
        return userRepository.findByUsername(user.username)
    }
}