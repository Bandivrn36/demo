package com.example.demo.repository

import com.example.demo.model.User
import com.example.demo.model.UserFile
import org.springframework.data.jpa.repository.JpaRepository
//import org.springframework.security.core.userdetails.User

import org.springframework.stereotype.Repository

@Repository
interface UserFileRepository : JpaRepository<UserFile,Long> {
    fun findByUser(user: User): List<UserFile>
    fun findByFilenameAndUser(filename: String, user: User): UserFile
    fun save(userFile: UserFile)
}