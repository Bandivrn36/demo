package com.example.demo.model

import org.springframework.security.core.authority.SimpleGrantedAuthority
import javax.persistence.*
import java.io.Serializable
import javax.validation.constraints.Email

//import javax.management.relation.Role

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var username: String = "",

    @Column(nullable = false)
    var password: String = "",

    @Column(nullable = false)
    var email: String = "",

    @Column(nullable = false)
    var enabled: Boolean = true

    //(fetch = FetchType.EAGER)
//    @JoinTable(
//        name = "user_roles",
//        joinColumns = [JoinColumn(name = "user_id")],
//        inverseJoinColumns = [JoinColumn(name = "role_id")]
//    )
//    var roles: List<com.example.demo.model.Role> = emptyList()
) : Serializable