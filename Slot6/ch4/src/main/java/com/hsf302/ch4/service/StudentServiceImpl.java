package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;


    //TODO 6

    @Override
    public long countStudents() {
        return studentRepository.count();
    }

    @Override
    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }

    //TODO 7

    @Override
    public List<Student> sapXepGPADesc() {
        return studentRepository.findAll(Sort.by(Sort.Direction.DESC, "gpa"));
    }

    @Override
    public Page<Student> phanTrang(int pageIndex, int size, String sortField) {
        if (pageIndex <0 || size <=0)
            throw new IllegalArgumentException("PageIndex phai >=0 va size phai >0");
        Pageable pageable = PageRequest.of(pageIndex,size, Sort.by(sortField).ascending());
        return studentRepository.findAll(pageable);
    }

    //TODO 8

    @Override
    public Optional<Student> timTheoMaSV(String studentCode) {
        return studentRepository.findByStudentCode(studentCode);
    }

    @Override
    public boolean kiemTraEmailTonTai(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public Long demStatusActive() {
        return studentRepository.countByActiveTrue();
    }
    //TODO 9

    @Override
    public List<Student> timSVTheoTen(String keyword) {
        return studentRepository.findStudentByFullNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Student> timSVTheoEmailDomain(String domain) {
        return studentRepository.findStudentByEmailEndingWith(domain);
    }

    @Override
    public List<Student> timSVKhongCoEmail() {
        return studentRepository.findStudentByEmailIsNull();
    }
    //TODO 10
    @Override
    public List<Student> timGPABetween(double min, double max) {
        return studentRepository.findStudentByGpaBetweenOrderByGpaDesc(min,max);
    }

    @Override
    public List<Student> timGenderActive(Gender gender) {
        return studentRepository.findByGenderAndActiveTrue(gender);
    }

    @Override
    public List<Student> timDobAfter(LocalDate date) {
        return studentRepository.findByDobAfter(date);
    }

    //TODO 11

    @Override
    public List<Student> timDepartment(String deptCode) {
        return studentRepository.findByDepartment_CodeOrderByFullNameAsc(deptCode);
    }

    @Override
    public long demSVTheoDepartment(String deptCode) {
        return studentRepository.countByDepartment_Code(deptCode);
    }

    @Override
    public List<Student> timTop3SVGpa() {
        return studentRepository.findTop3ByOrderByGpaDesc();
    }


}
