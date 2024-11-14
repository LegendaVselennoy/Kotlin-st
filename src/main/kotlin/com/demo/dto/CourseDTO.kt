package com.demo.dto

import jakarta.validation.constraints.NotBlank

data class CourseDTO(
    @get: NotBlank(message = "Not be blank")
    val name : String,
    val category: String
) {
}