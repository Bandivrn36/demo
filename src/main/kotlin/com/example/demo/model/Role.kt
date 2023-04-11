//package com.example.demo.model
//
//import javax.persistence.*
//
//@Entity
//@Table(name = "roles")
//data class Role(
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    var id: Long? = null,
//
//    @Column(nullable = false, unique = true)
//    var name: String = ""
//) {
//    constructor(name: String) : this(null, name)
//}