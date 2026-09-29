package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    //TODO 8
    Optional<Student> findByStudentCode(String studentCode);
    
    boolean existsByEmail(String email);
    
    Long countByActiveTrue();
    
    //TODO 9
    List<Student> findStudentByFullNameContainingIgnoreCase(String name);

    List<Student> findStudentByEmailEndingWith(String email);

    List<Student> findStudentByEmailIsNull();
    //Todo10
    List<Student> findStudentByGpaBetweenOrderByGpaDesc(Double min, Double max);
    List<Student> findByGenderAndActiveTrue(Gender gender);

    List<Student> findByDobAfter(LocalDate dobAfter);
}
