package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentExistsEmail() {
    //    given
        String email = "jamila@gmail.com";
        Student student = new Student("Jamila", email,Gender.FEMALE);
    //    when
        underTest.save(student);
        Boolean expected = underTest.selectExistsEmail(email);
    //    then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckIfStudentEmailDoesNotExist() {
        //    given
        String email = "jamila@gmail.com";
        Student student = new Student("Jamila", email,Gender.FEMALE);
        //    when
        Boolean expected = underTest.selectExistsEmail(email);
        //    then
        assertThat(expected).isFalse();
    }
}