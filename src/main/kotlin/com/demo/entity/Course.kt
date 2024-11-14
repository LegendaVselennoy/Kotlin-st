package com.demo.entity

import jakarta.persistence.*


@Entity
@Table(catalog = "test", schema = "dz",name = "courses")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long?,
    var name : String,
    var category: String
) {
}