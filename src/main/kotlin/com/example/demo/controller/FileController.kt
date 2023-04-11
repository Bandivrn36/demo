package com.example.demo.controller

import com.example.demo.model.User
import com.example.demo.model.UserFile
import com.example.demo.service.FileService
import com.example.demo.service.UserService
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletResponse
import com.example.demo.service.UserService.*
import org.springframework.security.core.Authentication
import java.nio.file.Paths

@Controller
@RequestMapping("/files")
class FileController(
    private val fileStorageService: FileService,
    private val userService: UserService
) {

    @GetMapping
    fun getAllFiles(model: Model, authentication: Authentication?): String {
        if (authentication != null && authentication.isAuthenticated) {
            val user = authentication.principal as User
            val files = fileStorageService.getAllFiles(user)
            model.addAttribute("files", files)
            return "file-list"
        }
        return "redirect:/login"
    }

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile, authentication: Authentication): String {
        val fileName = file.originalFilename
        val user = authentication.principal as User
        val uploadDir = Paths.get("uploads", user.id.toString()).toAbsolutePath()
        fileStorageService.storeFile(file, fileName!!, uploadDir)
        return "redirect:/files"
    }

    @GetMapping("/{fileName:.+}")
    fun downloadFile(@PathVariable fileName: String, response: HttpServletResponse, authentication: Authentication) {
        val user = authentication.principal as User
        val file = fileStorageService.getFileByName(fileName, user)
        response.contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE
        response.setHeader("Content-Disposition", "attachment; filename=\"${file?.filename}\"")
        response.outputStream.write(file?.content)
    }

    @GetMapping("/delete/{fileName:.+}")
    fun deleteFile(@PathVariable fileName: String, authentication: Authentication): String {
        val user = authentication.principal as User
        val file = fileStorageService.getFileByName(fileName, user)
        if (file != null) {
            fileStorageService.deleteFile(file)
        }
        return "redirect:/files"
    }
}