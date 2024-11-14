package com.demo.controller

import com.demo.dto.CourseDTO
import com.demo.service.CourseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
class CourseController(val courseService: CourseService) {

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable id:Long) = courseService.deleteCourse(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Valid courseDTO: CourseDTO): CourseDTO {
       return courseService.addCourse(courseDTO)
    }

    @GetMapping
    fun retrieveAllCourses(): List<CourseDTO> = courseService.retrieveAllCoursesService()

    @PutMapping("/{id}")
    fun updateCourse(@PathVariable id: Long, @RequestBody courseDTO: CourseDTO) =
        courseService.updateCourse(id, courseDTO)
}