package com.demo.controller

import com.demo.dto.CourseDTO
import com.demo.entity.Course
import com.demo.repository.CourseRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository


    @BeforeEach
    fun setUp(){
        courseRepository.deleteAll()
        val course = Course(null,"Name", "Category")
        val course2 = Course(null,"Name1", "Category1")
        val courses = listOf(course, course2)
        courseRepository.saveAll(courses)
    }

    @Test
    fun addInstructor() {

        val courseDTO = CourseDTO(
            "Name3", "Category3")


        val savedCourseDTO = webTestClient
            .post()
            .uri("/v1")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            savedCourseDTO!!.name!=null
        }
    }


    @Test
    fun addCourseInvlaidOInstructorId() {

        //given
        val courseDTO = CourseDTO(
            "Build RestFul APis using Spring Boot and Kotlin", "Dilip Sundarraj")

        //when
        val response = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        //then
        assertEquals("Instructor Id is not Valid!", response)
    }

    @Test
    fun retrieveAllCourses() {


        val courseDTOs = webTestClient
            .get()
            .uri("/v1")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        println("courseDTOs : $courseDTOs")

        assertEquals(2, courseDTOs!!.size)

    }

    @Test
    fun retrieveAllCourses_ByName() {

//        val uri = UriComponentsBuilder.fromUriString("/v1/courses")
//            .queryParam("course_name", "SpringBoot")
//            .toUriString()

        val courseDTOs = webTestClient
            .get()
            .uri("/v1")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        println("courseDTOs : $courseDTOs")

        assertEquals(2, courseDTOs!!.size)

    }

    @Test
    fun updateCourse() {

        val courseEntity = Course(null,
            "Name4", "Category4")
        courseRepository.save(courseEntity)
        val updatedCourseEntity = Course(null,
            "Apache Kafka for Developers using Spring Boot1", "Development" )

        val updatedCourseDTO = webTestClient
            .put()
            .uri("/v1/{id}", courseEntity.id)
            .bodyValue(updatedCourseEntity)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals("Apache Kafka for Developers using Spring Boot1", updatedCourseDTO?.name)

    }

    @Test
    fun deleteCourse() {
        val courseEntity = Course(null,
            "Apache Kafka for Developers using Spring Boot", "Development")

        courseRepository.save(courseEntity)
        webTestClient
            .delete()
            .uri("/v1/{id}", courseEntity.id)
            .exchange()
            .expectStatus().isNoContent
    }
}