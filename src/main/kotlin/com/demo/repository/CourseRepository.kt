package com.demo.repository

import com.demo.entity.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CourseRepository : JpaRepository<Course, Long> {
    fun findByNameContaining(courseName : String) : List<Course>

    @Query(value = "SELECT * FROM COURSES WHERE name like %?1%", nativeQuery = true)
    fun findCoursesByName(courseName : String) : List<Course>
}