package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Department;
import com.hsf302.ch4.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public long countDepartments() {
        return departmentRepository.count();
    }

    @Override
    public boolean findById(Long id) {
        return departmentRepository.existsById(id);
    }

    //TODO 11

    @Override
    public List<Department> timDepartmentWithoutSV() {
        return departmentRepository.findByStudentsIsEmpty();
    }
}
