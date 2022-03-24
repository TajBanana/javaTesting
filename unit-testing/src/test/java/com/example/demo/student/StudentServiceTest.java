package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    //private AutoCloseable autoCloseable;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        //if we have more than one mock in this test class, the keyword this will initialize all the mocks in this test class
        //autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new StudentService(studentRepository);
    }

/*    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }*/

    @Test
    void canGetAllStudents() {
    //    when
        underTest.getAllStudents();
    //    then
        verify(studentRepository).findAll(); //verify that this repo is invoked using the method findall()
    }

    @Test
    void canAddStudent() {
    //    given
        Student student = new Student(
                "Jamila",
                "jamila@gmail.com",
                Gender.FEMALE
        );
    //    when
        underTest.addStudent(student);
    //    then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();

        // need to capture the student that the method passed in is equals to the test student
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void canDeleteStudent() {
        //    given
        long studentId = anyLong();
        //    when
        given(studentRepository.existsById(studentId))
                .willReturn(true);
        underTest.deleteStudent(studentId);
        //    then
        ArgumentCaptor<Long> studentIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentRepository).deleteById(studentIdArgumentCaptor.capture());
        long capturedStudentId = studentIdArgumentCaptor.getValue();

        // need to capture the student that the method passed in is equals to the test student
        assertThat(capturedStudentId).isEqualTo(studentId);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        //    given
        Student student = new Student(
                "Jamila",
                "jamila@gmail.com",
                Gender.FEMALE
        );
        //    when
        //    then

        given(studentRepository.selectExistsEmail(anyString()))
                .willReturn(true);

        //underTest.addStudent(student);
        assertThatThrownBy(() -> underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken");

        verify(studentRepository, never()).save(any());
    }
}