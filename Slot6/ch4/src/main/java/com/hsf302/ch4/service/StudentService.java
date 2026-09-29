package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    //Todo 6
    long countStudents();
    Optional<Student> findStudentById(Long id);

    //Todo 7
    List<Student> SapXepGPADesc();
    Page<Student> phanTrang(int pageIndex, int size, String sortField);

}
