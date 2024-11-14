package com.demo.service

import com.demo.dto.CourseDTO
import com.demo.entity.Course
import com.demo.exception.CourseNotFoundException
import com.demo.repository.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {

    fun deleteCourse(courseId: Long): Any {
        val existingCourse = courseRepository.findById(courseId)

        return if (existingCourse.isPresent) {
            existingCourse.get().let {
                courseRepository.deleteById(courseId)
            }
        }else{
            throw CourseNotFoundException("No course found with id $courseId")
        }
    }

    fun updateCourse(courseId: Long, courseDTO: CourseDTO): CourseDTO {
        val existingCourse = courseRepository.findById(courseId);

    return  if(existingCourse.isPresent){
            existingCourse.get().let {
                it.name = courseDTO.name
                it.category = courseDTO.category
                courseRepository.save(it)
                CourseDTO(it.name, it.category)
            }
        }else{
            throw CourseNotFoundException("No course found")
        }
    }

    fun retrieveAllCoursesService(): List<CourseDTO> {

        return courseRepository.findAll()
            .map {
                CourseDTO(it.name, it.category)
            }

    }

    fun addCourse(courseDTO: CourseDTO) : CourseDTO {
        val courseEntity = courseDTO.let {
            Course(null, it.name, it.category)
        }

        courseRepository.save(courseEntity)

        return courseEntity.let {
            CourseDTO(it.name,it.category)
        }
    }
}