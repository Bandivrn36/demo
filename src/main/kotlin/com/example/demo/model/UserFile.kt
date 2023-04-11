package com.example.demo.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "user_files")
data class UserFile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    val filename: String,

    val fileSize: Long,

    val uploadDate: LocalDateTime = LocalDateTime.now(),

    val filepath: String,

   // val content: ByteArray,

    @Lob
    val content: ByteArray
    )
//) {
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as UserFile
//
//        if (id != other.id) return false
//        if (user != other.user) return false
//        if (filename != other.filename) return false
//        if (fileSize != other.fileSize) return false
//        if (uploadDate != other.uploadDate) return false
//        if (!data.contentEquals(other.data)) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = id.hashCode()
//        result = 31 * result + user.hashCode()
//        result = 31 * result + filename.hashCode()
//        result = 31 * result + fileSize.hashCode()
//        result = 31 * result + uploadDate.hashCode()
//        result = 31 * result + data.contentHashCode()
//        return result
//    }
//}