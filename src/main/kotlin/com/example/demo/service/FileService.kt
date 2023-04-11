package com.example.demo.service

import com.example.demo.model.User
import com.example.demo.model.UserFile
import com.example.demo.repository.UserFileRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import javax.transaction.Transactional

@Service
class FileService(private val userFileRepository: UserFileRepository) {

    private val uploadDir: Path = Paths.get("uploads")

    init {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir)
        }
    }

    @Transactional
    fun saveFile(multipartFile: MultipartFile, user: User) {
        try {
            val fileId = UUID.randomUUID().toString()
            val filename = "$fileId-${multipartFile.originalFilename}"
            val filepath = uploadDir.resolve(filename)

            Files.copy(multipartFile.inputStream, filepath)

            val userFile = multipartFile.originalFilename?.let {
                UserFile(
                    id = fileId,
                    filename = it,
                    filepath = filepath.toString(),
                    content = multipartFile.bytes,
                    user = user
                )
            }
            userFile?.let {
                userFileRepository.save(it)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun UserFile(id: String,filename: String,filepath: String,content: ByteArray,user: User): UserFile {
        TODO()
    }

    fun getAllFiles(user: User): List<UserFile> {
        return userFileRepository.findByUser(user)
    }

    fun deleteFile(userFile: UserFile) {
        try {
            Files.delete(Paths.get(userFile.filepath))
            userFileRepository.delete(userFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getFileByName(fileName: String, user: User): UserFile? {
        val userFile = userFileRepository.findByFilenameAndUser(fileName, user)
        return userFile?.let {
            val content = Files.readAllBytes(Paths.get(it.filepath))
            it.copy(content = content)
        }
    }

    fun storeFile(file: MultipartFile,fileName: String,uploadDir: Path) {
        try {
            val fileExtension = getFileExtension(fileName)
            val fileId = UUID.randomUUID().toString()
            val savedFileName = "$fileId$fileExtension"
            val savedFilePath = uploadDir.resolve(savedFileName)

            Files.copy(file.inputStream, savedFilePath, StandardCopyOption.REPLACE_EXISTING)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getFileExtension(fileName: String): String {
        val lastDotIndex = fileName.lastIndexOf('.')
        return if (lastDotIndex != -1) {
            fileName.substring(lastDotIndex)
        } else {
            ""
        }
    }
}