package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

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

    //TODO 10

    List<Student> findStudentByGpaBetweenOrderByGpaDesc(Double min, Double max);
    List<Student> findByGenderAndActiveTrue(Gender gender);

    List<Student> findByDobAfter(LocalDate dobAfter);

    //TODO 11
    List<Student> findByDepartment_CodeOrderByFullNameAsc(String code);   // JOIN departments ... WHERE d.code = ?
    long countByDepartment_Code(String code);
    List<Student> findTop3ByOrderByGpaDesc();// SELECT TOP 3 ... ORDER BY gpa DESC

    //TODO 12
    @Query("SELECT s FROM Student s WHERE s.department.code = :code AND s.gpa >= :minGpa ORDER BY s.gpa DESC")
    List<Student> findGoodStudentsInDepartment(@Param("code") String code,
                                               @Param("minGpa") double minGpa);



}
