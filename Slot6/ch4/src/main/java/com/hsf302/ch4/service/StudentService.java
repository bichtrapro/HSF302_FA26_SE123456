package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Student;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface StudentService {
    long countStudents();
    Optional<Student> findStudentById(Long id);
}
